package template.extensions

import com.kotlindiscord.kord.extensions.commands.Arguments
import com.kotlindiscord.kord.extensions.commands.converters.impl.optionalUser
import com.kotlindiscord.kord.extensions.commands.converters.impl.string
import com.kotlindiscord.kord.extensions.components.components
import com.kotlindiscord.kord.extensions.components.publicButton
import com.kotlindiscord.kord.extensions.components.types.emoji
import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.event
import com.kotlindiscord.kord.extensions.extensions.publicSlashCommand
import com.kotlindiscord.kord.extensions.types.respond
import dev.kord.common.Color
import dev.kord.common.annotation.KordPreview
import dev.kord.core.behavior.channel.createMessage
import dev.kord.core.behavior.interaction.edit
import dev.kord.core.entity.interaction.PublicFollowupMessage
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.rest.builder.message.create.embed
import dev.kord.rest.builder.message.modify.embed
import kotlinx.coroutines.flow.toList
import template.*
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@OptIn(KordPreview::class)
class Maine : Extension() {
    override val name = "Maine"

    @OptIn(ExperimentalTime::class)
    override suspend fun setup() {

        publicSlashCommand {
            name = "Cat"
            description = "Get a random cat picture from the Cat API"

            action {
                // Because of the DslMarker annotation KordEx uses, we need to grab Kord explicitly
                val kord = this@Maine.kord
                lateinit var message: PublicFollowupMessage

                message = respond {
                    embed {
                        image = CatAPI.random()
                        color = Color(255, 54, 54)
                    }
                    components(Duration.seconds(300)) {
                        onTimeout {
                            message.edit {
                                components = mutableListOf()
                            }
                        }
                        publicButton {
                            label = "Get a new cat"
                            emoji("🐱")
                            action {
                                message.edit {
                                    this.embed {
                                        image = CatAPI.random()
                                        color = Color(255, 54, 54)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        publicSlashCommand {
            name = "Dog"
            description = "Get a random cat picture from the Dog API"

            action {
                // Because of the DslMarker annotation KordEx uses, we need to grab Kord explicitly
                val kord = this@Maine.kord
                lateinit var message: PublicFollowupMessage

                message = respond {
                    embed {
                        image = DogAPI.random()
                        color = Color(255, 54, 54)
                    }
                    components(Duration.seconds(300)) {
                        onTimeout {
                            message.edit {
                                components = mutableListOf()
                            }
                        }
                        publicButton {
                            label = "Get a new dog"
                            emoji("🐶")
                            action {
                                message.edit {
                                    this.embed {
                                        image = DogAPI.random()
                                        color = Color(255, 54, 54)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        publicSlashCommand {
            name = "Pineapple"
            description = "Get a random pineapple from Unsplash"

            action {
                lateinit var message: PublicFollowupMessage
                var image = Unsplash.randomSearch(query = "Pineapple")

                message = respond {
                    embed {
                        this.image = image.urls.regular
                        color = Color(56, 143, 59)
                    }
                    this.content =
                        "Photo by [${image.user.username}](${image.links.html}) on [Unsplash](https://unsplash.com)"
                    components(Duration.seconds(300)) {
                        onTimeout {
                            message.edit {
                                components = mutableListOf()
                            }
                        }
                        publicButton {
                            label = "Get a new pineapple"
                            emoji("🍍")
                            action {
                                image = Unsplash.randomSearch(query = "Pineapple")
                                message.edit {
                                    this.content =
                                        "Photo by [${image.user.username}](${image.links.html}) on " +
                                                "p[Unsplash](https://unsplash.com)"
                                    this.embed {
                                        this.image = image.urls.regular
                                        color = Color(56, 143, 59)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        publicSlashCommand {
            name = "Melon"
            description = "Get a random melon from Unsplash"

            action {
                lateinit var message: PublicFollowupMessage
                var image = Unsplash.randomSearch(query = "Melon")

                message = respond {
                    embed {
                        this.image = image.urls.regular
                        color = Color(255, 178, 36)
                    }
                    this.content =
                        "Photo by [${image.user.username}](${image.links.html}) on [Unsplash](https://unsplash.com)"
                    components(Duration.seconds(300)) {
                        onTimeout {
                            message.edit {
                                components = mutableListOf()
                            }
                        }
                        publicButton {
                            label = "Get a new Melon"
                            emoji("🍉")
                            action {
                                image = Unsplash.randomSearch(query = "Melon")
                                message.edit {
                                    this.content =
                                        "Photo by [${image.user.username}](${image.links.html}) on " +
                                                "[Unsplash](https://unsplash.com)"
                                    this.embed {
                                        this.image = image.urls.regular
                                        color = Color(255, 178, 36)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        publicSlashCommand(::HugArgs) {
            name = "hug"
            description = "Give someone a hug or receive one from Maine"

            action {
                respond {
                    if (arguments.receiver == null || arguments.receiver == user) {
                        content = "${user.mention} Have a special hug from me ❤️"
                        embed {
                            image = "https://c.tenor.com/eAKshP8ZYWAAAAAC/cat-love.gif"
                            color = Color(255, 217, 217)
                        }
                    } else {
                        content = "${arguments.receiver!!.mention} Have a hug from ${user.mention}"
                        embed {
                            image =
                                "https://media1.tenor.com/images/2d4138c7c24d21b9d17f66a54ee7ea03/tenor.gif?itemid=12535134)"
                            color = Color(255, 217, 217)
                        }
                    }
                }
            }
        }
        publicSlashCommand(::LyricsArgs) {
            name = "Lyrics"
            description = "Find the lyrics of a song from Genius"

            action {
                val song = RandomAPI.lyrics(song = arguments.lyrics)
                if (song.lyrics.length >= 2000) {
                    respond {
                        embed {
                            description =
                                "The lyrics you requested are too long to fit in Discord, " +
                                        "but you can view them directly on Genius [here](${song.links.genius}.)."
                            this.color = Color(242, 201, 17)
                        }
                    }
                } else {
                    respond {
                        embed {
                            this.color = Color(242, 201, 17)
                            title = song.title
                            author {
                                name = song.author
                            }
                            thumbnail {
                                url = song.thumbnail.genius
                            }
                            description = song.lyrics
                            footer {
                                text = "Source: ${song.links.genius}"
                            }
                        }
                    }
                }
            }
        }
        event<MessageCreateEvent> {
            action {
                if (kord.getSelf() in this.event.message.mentionedUsers.toList()) {
                    event.message.channel.createMessage {
                        embed {
                            image = "https://i.imgflip.com/3ia3r2.png"
                            this.color = Color(red = 193, green = 0, blue = 238)
                        }
                    }
                }
            }
        }
    }

    inner class HugArgs : Arguments() {
        val receiver by optionalUser(
            "receiver",
            description = "Receiver of your hug",
        )
    }

    inner class LyricsArgs : Arguments() {
        val lyrics by string(
            "song",
            description = "Name of the song"
        )
    }
}
