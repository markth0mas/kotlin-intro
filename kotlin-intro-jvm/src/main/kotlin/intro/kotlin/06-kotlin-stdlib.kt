package intro.kotlin

import java.io.File
import java.io.FileInputStream
import javax.swing.JFrame

// Taken from http://beust.com/weblog/2015/10/30/exploring-the-kotlin-standard-library/

data class DbConnection(val url: String);

fun getConnection(name: String) = DbConnection(name)
fun getConnectionIfKnown(name: String): DbConnection? = null

fun main(args: Array<String>) {
    // apply() runs a code block on a receiver and then returns the receiver
    val f: File = File("hello").apply { mkdirs() }

    // let allows you to limit scope of a variable to a specific block
    getConnection("UAT-DB").let { connection ->
        println(connection.url)
    }

    // Idiomatic to use with ?
    getConnectionIfKnown("DEV-DB")?.let { connection ->
        println("${connection.url} recognised")
    }

    // Change receiver
    with(JFrame()) {
        title = "Test frame"
    }

    // Use a closeable
    FileInputStream("test.json").use {
        // Do something with it
    }
}