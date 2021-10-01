package template.extensions

import com.kotlindiscord.kord.extensions.commands.Arguments
import com.kotlindiscord.kord.extensions.commands.converters.impl.optionalUser
import com.kotlindiscord.kord.extensions.commands.converters.impl.user
import com.kotlindiscord.kord.extensions.components.components
import com.kotlindiscord.kord.extensions.components.publicButton
import com.kotlindiscord.kord.extensions.components.types.emoji
import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.publicSlashCommand
import com.kotlindiscord.kord.extensions.types.respond
import dev.kord.common.annotation.KordPreview
import dev.kord.core.behavior.interaction.edit
import dev.kord.core.entity.interaction.PublicFollowupMessage
import dev.kord.rest.builder.message.create.allowedMentions
import dev.kord.rest.builder.message.create.embed
import dev.kord.rest.builder.message.modify.embed
import template.CatAPI
import template.TEST_SERVER_ID
import template.Unsplash
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


            guild(TEST_SERVER_ID)  // Otherwise it'll take an hour to update

            action {
                // Because of the DslMarker annotation KordEx uses, we need to grab Kord explicitly
                val kord = this@Maine.kord
                lateinit var message: PublicFollowupMessage


                message = respond {
                    embed {
                        image = CatAPI.random()
                    }
                    components(Duration.seconds(300)) {
                        onTimeout {
                            message.edit {
                                components = mutableListOf()
                            }
                        }
                        publicButton {
                            label = "Get a new cat"
                            emoji("🐈")
                            action {
                                message.edit {
                                    this.embed {
                                        image = CatAPI.random()
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

            guild(TEST_SERVER_ID)  // Otherwise it'll take an hour to update

            action {
                lateinit var message: PublicFollowupMessage
                var image = Unsplash.randomSearch(query = "Pineapple")

                message = respond {
                    embed {
                        this.image = image.urls.regular
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
                                    this.embed {
                                        this.image = image.urls.regular
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

            guild(TEST_SERVER_ID)  // Otherwise it'll take an hour to update

            action {
                lateinit var message: PublicFollowupMessage
                var image = Unsplash.randomSearch(query = "Melon")

                message = respond {
                    embed {
                        this.image = image.urls.regular
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
                                    this.embed {
                                        this.image = image.urls.regular
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        publicSlashCommand(::HugArgs)
        {
            name = "hug"
            description = "Give someone a hug or receive one from Maine"

            guild(TEST_SERVER_ID)  // Otherwise it'll take an hour to update
            action {
                respond {
                    if(arguments.receiver == null || arguments.receiver == user) {
                        content = "${user.mention} Have a special hug from me ❤️"
                        embed {
                            image = "https://c.tenor.com/eAKshP8ZYWAAAAAC/cat-love.gif"
                        }

                    } else {
                        content = "${arguments.receiver!!.mention} Have a hug from ${user.mention}"
                        embed {
                            image = "https://media1.tenor.com/images/2d4138c7c24d21b9d17f66a54ee7ea03/tenor.gif?itemid=12535134)"
                        }
                    }
                }
            }
        }
    }

    inner class HugArgs : Arguments() {
        val receiver by optionalUser(
            "receiver",
            description = "Receiver of your hug",)



    }
}

