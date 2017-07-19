package intro.kotlin

import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.should.shouldMatch
import org.http4k.client.OkHttp
import org.http4k.core.*
import org.http4k.core.Method.GET
import org.http4k.core.Method.PUT
import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.core.Status.Companion.OK
import org.http4k.format.Jackson.auto
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.*

class ContactsAppServerTest {
    private val port = 8095
    private val client = OkHttp()
    private val repository = ContactsRepository()
    private val app = contactsAppServer(port, repository)
    private val contactLens = Body.auto<Contact>().toLens()

    @Before
    fun setup() {
        repository.save("1", aContact())

        app.start()
    }

    @After
    fun teardown() {
        app.stop()
    }

    @Test
    fun `endpoints are mounted`() {
        client(Request(GET, "http://localhost:$port/contacts")).statusShouldBeOk()
        client(Request(GET, "http://localhost:$port/contacts/1")).statusShouldBeOk()

        val newContact = aContact()
        client(
                Request(PUT, "http://localhost:$port/contacts/1")
                        .with(contactLens of newContact)
        ).statusShouldBeOk()
    }
}

class ContactsAppTest {
    private val contactsRepository = ContactsRepository()
    private val app = contactsApp(contactsRepository)
    private val contactLens = Body.auto<Contact>().toLens()
    private val contactsLens = Body.auto<Array<Contact>>().map(Array<Contact>::toList).toLens()

    @Test
    fun `can get all contacts`() {
        val contact = aContact()
        contactsRepository.save("1", contact)

        val response = app(Request(GET, "/contacts")).statusShouldBeOk()
        response.shouldHaveContacts(listOf(contact.copy(id = "1")))
    }

    @Test
    fun `new contacts are stored with ID`() {
        val contact = aContact()
        val response = app(Request(PUT, "/contacts/1")
                .with(contactLens of contact))
                .statusShouldBeOk()

        response.shouldHaveContact(contact.copy(id = "1"))
    }

    @Test
    fun `can add new contact and retrieve it`() {
        val contact = aContact()
        app(Request(PUT, "/contacts/1").with(contactLens of contact)).statusShouldBeOk()

        val response = app(Request(GET, "/contacts/1")).statusShouldBeOk()
        response.shouldHaveContact(contact.copy(id = "1"))
    }

    @Test
    fun `returns not found for unknown contacts`() {
        app(Request(GET, "/contacts/1")).statusShouldBe(NOT_FOUND)
    }

    private fun Response.shouldHaveContact(contact: Contact) {
        contactLens(this) shouldMatch equalTo(contact)
    }

    private fun Response.shouldHaveContacts(contacts: List<Contact>) {
        contactsLens(this) shouldMatch equalTo(contacts)
    }
}

private fun aContact() = Contact(id = null, name = "Contact ${UUID.randomUUID()}")

private fun Response.statusShouldBe(expectedStatus: Status) = apply {
    status shouldMatch equalTo(expectedStatus)
}

private fun Response.statusShouldBeOk() = statusShouldBe(OK)