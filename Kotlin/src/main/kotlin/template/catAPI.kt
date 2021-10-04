package template

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: Int,
    val name: String
)

@Serializable
data class Cat(
    val categories: List<Category> = listOf(),
    val id: String,
    val url: String,
    val width: Int,
    val height: Int
)

object CatAPI {
    suspend fun random(): String {
        val http = HttpClient {
            install(JsonFeature) {
                serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                    ignoreUnknownKeys = true
                })
            }
        }
        val request: List<Cat> = http.get("https://api.thecatapi.com/v1/images/search")
        return request[0].url
    }
}
