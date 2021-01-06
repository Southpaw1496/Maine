from discord.ext import commands
from discord import Embed, Colour
from os import environ
import requests
from discord import Intents
intents = Intents.default()
intents.members = True
bot = commands.Bot(command_prefix="£", intents=intents)
unsplashToken = environ.get("UNSPLASH_COON")
intents.members = True

@bot.event
async def on_ready():
    print("Bot logged in")

@bot.command()
async def cat(context):
    request = requests.get("https://api.thecatapi.com/v1/images/search")
    requestJson = request.json()
    embed = Embed()
    embed.set_image(url=requestJson[0]["url"])
    await context.send(embed=embed)

@bot.command()
async def dog(context):
    request = requests.get("https://api.thedogapi.com/v1/images/search")
    requestJson = request.json()
    embed = Embed()
    embed.set_image(url=requestJson[0]["url"])
    await context.send(embed=embed)

@bot.command()
async def pineapple(context):
    request = requests.get("https://api.unsplash.com/photos/random/", params={"query": "Pineapple", "username": "pineapple"}, headers={"Authorization": unsplashToken} )
    requestJson = request.json()
    unsplashAuthor = requestJson["user"]["name"]
    embed = Embed()
    embed.set_image(url=requestJson["urls"]["regular"])
    embed.set_footer(text=f'Photo by {unsplashAuthor} on Unsplash')
    await context.send(embed=embed)
@bot.command()
async def lyrics(context, *, song):
    request = requests.get("https://some-random-api.ml/lyrics", params={"title": song})
    requestJson = request.json()
    songTitle = requestJson["title"]
    songAuthor = requestJson["author"]
    songThumbnail = requestJson["thumbnail"]["genius"]
    songLyrics = requestJson["lyrics"]
    songLyricsLink = requestJson["links"] ["genius"]
    if len(songLyrics) > 2000:
        embed = Embed(
            title = songTitle,
            description = f"Your song was too long to fit in Discord! Lyrics are available at {songLyricsLink}"
        )
    else:
        embed = Embed(
        title = songTitle,
        description = songLyrics,)
        embed.set_footer = f"Source: {songLyricsLink}"
    embed.set_author(name=songAuthor)
    embed.set_thumbnail(url=songThumbnail)
    await context.send(embed=embed)

@bot.event
async def on_message(message):
    await bot.process_commands(message)
    if bot.user.mentioned_in(message):
        embed = Embed(colour=Colour.from_rgb(193, 0, 238))
        embed.set_image(url="https://i.imgflip.com/3ia3r2.png")
        await message.channel.send(embed=embed)
    elif message.content == "Silencio!":
        if message.message.author.server_permissions.manage_roles:
            silencee = None
            async for m in message.channel.history():
                if m.id != message.message.id:
                    silencee = m.author
                    break
            if silencee:
                role = m.guild.get_role("Muted")
                await silencee.add_roles(role)
    
        
            
bot.run(environ.get("DISCORD_COON"))