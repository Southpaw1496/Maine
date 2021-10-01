package template

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.serialization.Serializable


@Serializable
data class Dog(
    val categories: List<Category> = listOf(),
    val id: String,
    val url: String,
    val width: Int,
    val height: Int
)

object DogAPI {
    suspend fun random(): String {
        val http = HttpClient {
            install(JsonFeature) {
                serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                    ignoreUnknownKeys = true
                })
            }
        }
        val request: List<Cat> = http.get("https://api.thedogapi.com/v1/images/search")
        return request[0].url
    }
}