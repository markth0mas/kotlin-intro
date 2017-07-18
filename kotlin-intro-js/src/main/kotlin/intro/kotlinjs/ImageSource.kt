package intro.kotlinjs

import org.w3c.fetch.RequestInit
import kotlin.browser.window

data class Image(val url: String, val width: Int, val height: Int)

class ImageSource {
    suspend fun fetchImage(subject: String): Image {
        val giphyPublicBetaKey = "dc6zaTOxFJmzC"
        val url = "https://api.giphy.com/v1/gifs/random?api_key=$giphyPublicBetaKey&tag=$subject"

        return fetch(url) { body ->
            val data = body.data

            Image(
                    url = data.fixed_height_downsampled_url as String,
                    width = (data.fixed_height_downsampled_width as String).toInt(),
                    height = (data.fixed_height_downsampled_height as String).toInt()
            )
        }
    }

    private suspend fun <T> fetch(url: String, parser: (dynamic) -> T): T {
        val request = object : RequestInit {
            override var method: String? = "GET"
        }

        val response = window.fetch(url, request).await()
        val body: dynamic = response.json().await()

        return parser(body)
    }
}