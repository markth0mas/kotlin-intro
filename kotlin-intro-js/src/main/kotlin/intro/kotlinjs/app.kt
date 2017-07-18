package intro.kotlinjs

import kotlin.browser.document

fun main(args: Array<String>) {
    val fetcher = ImageSource()
    val app = ImageLoaderApplication(fetcher)

    val target = document.getElementById("app")

    if (target != null) {
        target.appendChild(app.render())
    } else {
        console.error("Cannot find application target")
    }
}

