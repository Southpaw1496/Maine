package template.extensions

import com.kotlindiscord.kord.extensions.components.components
import com.kotlindiscord.kord.extensions.components.publicButton
import com.kotlindiscord.kord.extensions.components.types.emoji
import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.publicSlashCommand
import com.kotlindiscord.kord.extensions.types.respond
import dev.kord.common.annotation.KordPreview
import dev.kord.core.behavior.interaction.edit
import dev.kord.core.entity.interaction.PublicFollowupMessage
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
            description =  "Get a random pineapple from Unsplash"

            guild(TEST_SERVER_ID)  // Otherwise it'll take an hour to update

            action {
                lateinit var message: PublicFollowupMessage
                var image = Unsplash.randomSearch(query = "Pineapple")

                message = respond {
                    embed {
                        this.image = image.urls.regular
                    }
                    this.content = "Photo by [${image.user.username}](${image.links.html}) on [Unsplash](https://unsplash.com)"
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
            description =  "Get a random melon from Unsplash"

            guild(TEST_SERVER_ID)  // Otherwise it'll take an hour to update

            action {
                lateinit var message: PublicFollowupMessage
                var image = Unsplash.randomSearch(query = "Melon")

                message = respond {
                    embed {
                        this.image = image.urls.regular
                    }
                    this.content = "Photo by [${image.user.username}](${image.links.html}) on [Unsplash](https://unsplash.com)"
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
    }
}