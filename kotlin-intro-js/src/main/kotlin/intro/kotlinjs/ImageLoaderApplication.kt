package intro.kotlinjs

import kotlinx.html.*
import kotlinx.html.dom.create
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.Node
import kotlin.browser.document

class ImageLoaderApplication(val source: ImageSource) {
    fun render() = document.create.div {
        h1 {
            +"Kotlin Image Loader"
        }

        div {
            textInput {
                id = "subject"
                size = "25"
            }

            button {
                +"Load"

                onClickFunction = {
                    val subjectElement = document.getElementById("subject") as HTMLInputElement
                    val subject = subjectElement.value

                    async {
                        val image = source.fetchImage(subject)

                        document.getElementById("images")!!.appendChild(image.presenter())
                    }
                }
            }

            div {
                id = "images"
            }
        }
    }

    private fun Image.presenter(): Node {
        return document.create.img {
            src = url
            width = this@presenter.width.toString()
            height = this@presenter.height.toString()
        }
    }
}