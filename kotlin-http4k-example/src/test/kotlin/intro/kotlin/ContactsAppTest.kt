package intro.kotlin

import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.should.shouldMatch
import org.http4k.client.OkHttp
import org.http4k.core.*
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.OK
import org.http4k.format.Jackson.auto
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.*

class ContactsAppServerTest {
    private val port = 8095
    private val client = OkHttp()
    private val app = contactsAppServer(port)

    @Before
    fun setup() {
        app.start()
    }

    @After
    fun teardown() {
        app.stop()
    }

    @Test
    fun `endpoints are mounted`() {
        client(Request(GET, "http://localhost:$port/contacts")).statusShouldBeOk()
        client(Request(GET, "http://localhost:$port/contacts/0")).statusShouldBeOk()

        val contactLens = Body.auto<Contact>().toLens()
        client(Request(POST, "http://localhost:$port/contacts").with(contactLens of aContact())).statusShouldBe(CREATED)
    }
}

class ContactsAppTest {
    private val app = contactsApp()

    @Test
    fun `can get all contacts`() {
        val response = app(Request(GET, "/contacts"))

        response.statusShouldBeOk()
    }
}

private fun aContact() = Contact(id = null, name ="Contact ${UUID.randomUUID()}")

private fun Response.statusShouldBe(expectedStatus: Status) {
    status shouldMatch equalTo(expectedStatus)
}

private fun Response.statusShouldBeOk() = statusShouldBe(OK)