package intro.kotlin

class ContactsRepository {
    private val contacts = mutableMapOf<String, Contact>()

    fun all() = contacts.values.toList()

    fun find(id: String) = contacts[id]

    fun save(id: String, contact: Contact): Contact {
        val contactWithId = contact.copy(id = id)
        contacts[id] = contactWithId

        return contactWithId
    }
}
