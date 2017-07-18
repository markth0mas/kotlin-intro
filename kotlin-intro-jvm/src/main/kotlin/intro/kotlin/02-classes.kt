package intro.kotlin

// Kotlin classes are closed (final) by default
// (Effective Java: Design and document for inheritance or prohibit it)
// Check the bytecode if you are interested - CTRL+SHIFT+A in IntelliJ and then choose 'Show Kotlin Bytecode'

// Class has a single Primary constructor taking one value, this is not stored as a field
    class Person constructor(age: Int) {
    // Property
    // Will be calculated at construction time
    val isRetired = age >= 65
}




// Shortcut to store properties
class Address(val street: String, val city: String)






// Can also provide a custom set or get implementation
class Counter {
    var current: Int = 0
        private set
}




// There can be only one (Singleton)
object Highlander {
    val name = "Connor MacLeod"
}





// Kotlin doesn't have static methods, you can just use package level functions instead
// But, if you need access to class internals then you can use a companion object
class Player private constructor (val name: String) {
    companion object {
        var nextPlayerNumber: Int = 1

        fun create() = Player("Player ${nextPlayerNumber++}")
    }
}




fun main(args: Array<String>) {
    val mark = Person(37)
    println("Mark is retired: ${mark.isRetired}")     // Mark is retired: false

    val address = Address("New Bond Street", "London")
    println(address.city)    // London

    val counter = Counter()
    println(counter.current) // 0

    println(Highlander.name) // Connor MacLeod

    val player1 = Player.create()
    println(player1.name)    // Player 1

    val player2 = Player.create()
    println(player2.name)    // Player 2
}