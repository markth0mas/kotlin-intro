package intro.kotlin

import io.dropwizard.jersey.params.IntParam
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriInfo

data class Contact(val id: Int?, val name: String)

@Path("/contacts")
class ContactsResource {
    val contacts = mutableListOf<Contact>(
            Contact(0, "Mark Thomas")
    )

    // curl http://localhost:8080/contacts
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    fun all() = contacts

    // curl http://localhost:8080/contacts/0
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun get(@PathParam("id") id: IntParam) =
            contacts.getOrNull(id.get()) ?: throw NotFoundException("No contact with id $id")

    // curl -X POST -H "Content-Type: application/json" -d '{"name": "Father Christmas"}' http://localhost:8080/contacts/
    @POST
    @Path("/")
    @Consumes("application/json")
    fun create(contact: Contact, @Context uriInfo: UriInfo): Response {
        val idOfNewContact = contacts.size + 1
        contacts += contact.copy(id = idOfNewContact)

        return Response.created(
                uriInfo.absolutePathBuilder.path(idOfNewContact.toString()).build()
        ).build()
    }
}