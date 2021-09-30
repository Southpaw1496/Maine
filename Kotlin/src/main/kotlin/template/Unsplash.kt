package template

import com.kotlindiscord.kord.extensions.utils.env
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.serialization.Serializable

@Serializable
data class UnsplashRandom(
    val id: String?,
    val created_at: String?,
    val updated_at: String?,
    val promoted_at: String?,
    val width: Int,
    val height: Int,
    val color: String?,
    val blur_hash: String?,
    val description: String?,
    val alt_description: String?,
    val urls: Urls,
    val links: Links,
    val likes: Int,
    val liked_by_user: Boolean,
    val sponsorship: String?,
    val user: User,
    val exif: Exif,
    val views: Int,
    val downloads: Int,
) {
    @Serializable
    data class Urls (
        val raw: String,
        val full: String,
        val regular: String,
        val small: String,
        val thumb: String,
            )
    @Serializable
    data class Links (
        val self: String?,
        val html: String?,
        val download: String?,
        val download_location: String?,
        )
    @Serializable
    data class User (
        val id: String?,
        val username: String?,
            )
    @Serializable
    data class Exif (
        val make: String?,
        val model: String?,
        val exposure_time: String?,
        val aperture: String?,
        var focal_length: String?,
        var iso: Int?
            )
}

object Unsplash {
    suspend fun randomSearch(query: String): UnsplashRandom {
        val http = HttpClient {
            install(JsonFeature) {
                serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                    ignoreUnknownKeys = true
                })
            }
        }
        val request: UnsplashRandom = http.get("https://api.unsplash.com/photos/random/") {
            headers {
                append("Authorization", env("API_UNSPLASH"))
            }
            parameter("query", query)
        }
        return request
    }
}