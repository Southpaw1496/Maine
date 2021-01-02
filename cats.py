import discord

client = discord.Client()

@client.event
async def on_ready():
    print('We have logged in as {0.user}'.format(client))

@client.event
async def on_message(message):
    if message.author == client.user:
        return

    if message.content.startswith('£cat'):
        embed = discord.Embed()
        embed.set_image(url="https://source.unsplash.com/random/?cat")
        await message.channel.send(embed=embed)

client.run(token)