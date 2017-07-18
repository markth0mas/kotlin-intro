package intro.kotlin

// Delegated properties allow libraries to implement common access strategies
class Cpu {
    val performAReallyExpensiveCalculation by lazy {
        "IT TAKES TIME TO CALCULATE ME"
    }
}

class JsonBackedPerson(fields: MutableMap<String,Any?>) {
    val name: String by fields
}

fun main(args: Array<String>) {
    val cpu = Cpu()
    println(cpu.performAReallyExpensiveCalculation)

    val jsonBackedPerson = JsonBackedPerson(mutableMapOf("name" to "Mark"))
    println(jsonBackedPerson.name)
}