package intro.kotlin

import org.http4k.core.*
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.core.Status.Companion.OK
import org.http4k.filter.DebuggingFilters
import org.http4k.filter.ServerFilters
import org.http4k.format.Jackson.auto
import org.http4k.lens.Path
import org.http4k.lens.int
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer

data class Contact(val id: Int?, val name: String)

fun main(args: Array<String>) {
    val contacts = mutableListOf<Contact>(
            Contact(0, "Mark Thomas")
    )

    val idLens = Path.int().of("id")
    val contactsLens = Body.auto<List<Contact>>().toLens()
    val contactLens = Body.auto<Contact>().toLens()

    val httpHandler = DebuggingFilters
            .PrintRequestAndResponse()
            .then(ServerFilters.CatchLensFailure)
            .then(routes(
                    "/" to GET bind {
                        Response(OK).with(contactsLens of contacts)
                    },
                    "/{id:.+}" to GET bind { request ->
                        contacts.find { it.id == idLens(request) }?.let { Response(OK) } ?: Response(NOT_FOUND)
                    },
                    "/" to POST bind { request ->
                        val idOfNewContact = contacts.size + 1
                        contacts += contactLens(request).copy(id = idOfNewContact)

                        Response(CREATED).header("Location", request.uri.pathWith(idOfNewContact.toString()))
                    }
            ).withBasePath("/contacts"))

    httpHandler.asServer(Jetty(8090)).startAndBlock()
}

private fun Uri.pathWith(extension: String, separator: Char = '/') =
        if (path.last() == separator) {
            path + extension
        } else {
            path + separator + extension
        }

