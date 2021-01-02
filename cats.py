from discord.ext import commands
from discord import Embed, Colour
from os import environ
import requests
bot = commands.Bot(command_prefix="£")

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

@bot.event
async def on_message(summon):
    await bot.process_commands(summon)
    if bot.user.mentioned_in(summon):
        embed = Embed(colour=Colour.from_rgb(193, 0, 238))
        embed.set_image(url="https://i.imgflip.com/3ia3r2.png")
        await summon.channel.send(embed=embed)

bot.run(environ.get("COON_BOT"))