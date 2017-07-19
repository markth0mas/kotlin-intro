package intro.kotlin

import org.http4k.core.*
import org.http4k.core.Method.GET
import org.http4k.core.Method.PUT
import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.core.Status.Companion.OK
import org.http4k.filter.DebuggingFilters
import org.http4k.filter.ServerFilters
import org.http4k.format.Jackson.auto
import org.http4k.lens.Path
import org.http4k.lens.string
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Http4kServer
import org.http4k.server.Jetty
import org.http4k.server.asServer

fun main(args: Array<String>) {
    contactsAppServer().startAndBlock()
}

fun contactsAppServer(port: Int = 8090, contactsRepository: ContactsRepository = ContactsRepository()): Http4kServer {
    return contactsApp(contactsRepository).asServer(Jetty(port))
}

fun contactsApp(contacts: ContactsRepository): HttpHandler {
    val idLens = Path.string().of("id")
    val contactsLens = Body.auto<List<Contact>>().toLens()
    val contactLens = Body.auto<Contact>().toLens()

    return DebuggingFilters
            .PrintRequestAndResponse()
            .then(ServerFilters.CatchLensFailure)
            .then(routes(
                    "/{id:.+}" to GET bind { request ->
                        val contact = contacts.find(idLens(request))
                        contact?.let { Response(OK).with(contactLens of contact) } ?: Response(NOT_FOUND)
                    },
                    "/{id:.+}" to PUT bind { request ->
                        val newContact = contacts.save(idLens(request), contactLens(request))

                        Response(OK).with(contactLens of newContact)
                    },
                    "/" to GET bind {
                        Response(OK).with(contactsLens of contacts.all())
                    }
            ).withBasePath("/contacts"))
}

