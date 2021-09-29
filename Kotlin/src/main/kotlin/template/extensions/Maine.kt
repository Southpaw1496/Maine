package template.extensions

import com.kotlindiscord.kord.extensions.components.components
import com.kotlindiscord.kord.extensions.components.publicButton
import com.kotlindiscord.kord.extensions.components.types.emoji
import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.publicSlashCommand
import com.kotlindiscord.kord.extensions.types.respond
import com.kotlindiscord.kord.extensions.utils.toReaction
import dev.kord.common.annotation.KordPreview
import dev.kord.core.behavior.interaction.edit
import dev.kord.core.entity.interaction.PublicFollowupMessage
import dev.kord.rest.builder.message.create.embed
import dev.kord.rest.builder.message.modify.embed
import template.CatAPI
import template.TEST_SERVER_ID
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
    }
}