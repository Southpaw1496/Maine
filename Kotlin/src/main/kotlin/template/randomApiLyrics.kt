package template

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.get
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.serialization.Serializable

@Serializable
data class Lyrics(
    val title: String,
    val author: String,
    val lyrics: String,
    val thumbnail: Thumbnail,
    val links: Thumbnail
) {
    @Serializable
    data class Thumbnail(
        val genius: String
    )

    @Serializable
    data class Links(
        val genius: String
    )
}

object RandomAPI {
    suspend fun lyrics(song: String): Lyrics {
        val http = HttpClient {
            install(JsonFeature) {
                serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                    ignoreUnknownKeys = true
                })
            }
        }
        return http.get("https://some-random-api.ml/lyrics") {
            parameter("title", song)
        }

    }
}
