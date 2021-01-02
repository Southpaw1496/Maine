from discord.ext import commands
from discord import Embed
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
    


bot.run(environ.get("COON_BOT"))