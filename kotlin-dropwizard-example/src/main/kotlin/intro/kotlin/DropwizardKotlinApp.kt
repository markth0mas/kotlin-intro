package intro.kotlin

import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.dropwizard.Application
import io.dropwizard.Configuration
import io.dropwizard.setup.Environment

fun main(args: Array<String>) {
    DropwizardKotlinApp().run(*args)
}

class DropwizardKotlinApp : Application<Configuration>() {
    override fun run(configuration: Configuration, environment: Environment) {
        with(environment.objectMapper) {
            // Teach Jackson how to construct data classes
            registerModule(KotlinModule())
        }

        with(environment.jersey()) {
            register(ContactsResource())
        }
    }
}