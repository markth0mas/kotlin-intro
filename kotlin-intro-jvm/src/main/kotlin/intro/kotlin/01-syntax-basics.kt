package intro.kotlin

fun main(args: Array<String>) {
    // 1. Read-only variable
    val team = "Chelsea"

    // 2. Mutable variable
    var numberOfGoals = 1
    numberOfGoals++









    // 3. Function
    fun double(n: Int) = n * 2

    // 4. Function types
    val anonDouble: (Int) -> Int = { n -> n * 2 }
    println(anonDouble.invoke(3))  // 6








    // 5. String templates/interpolation
    println("$team scored $numberOfGoals goals")  // Chelsea scored 2 goals








    // 6. All control structures (except variable assignment) are expressions
    // useful for composition
    fun max(a: Int, b: Int) = if (a > b) a else b








    // 7. Null safety and smart casts
    // 'Null references: Billion Dollar Mistake' (Tony Hoare)
    fun parseInt(s: String): Int? {
        try {
            return Integer.parseInt(s)
        } catch(e: NumberFormatException) {
            return null
        }
    }

    val two = parseInt("2")
    val four = parseInt("4")

    // Not valid!
    // println(two * four)
    if (two != null && four != null) {
        println(two * four)
    }






    // 8. Elvis operator
    val country: String? = null
    println(country ?: "Unknown")







    // 9. Ranges
    val firstFive = 1..5

    for (n in firstFive) {
        println(n)
    }

    // 10. More complex - remember this example, we'll come back to it!
    // Prints 9, 6, 3, 0
    for (n in 9 downTo 0 step 3) {
        println(n)
    }





    // 11. when expressions
    // smart casts work here too
    // anything that is a function or a value can be used
    // no need for break
    fun describe(obj: Any) =
            when (obj) {
                1          -> "One"
                "Hello"    -> "Greeting"
                in 1..10   -> "Top 10"
                is Long    -> "Long"
                is String  -> "String of ${obj.length} characters"
                else       -> "You got me"
            }





    // 12. Constructing an object (no new)
    val usd = Currency("USD")

    // 13. == doesn't mean what it does in Java
    val usd2 = Currency("USD")

    println(usd == usd2)   // true
    println(usd === usd2)  // false






    // 14. Extension methods (no more StringUtils!)
    // Check out the it and notice how the code block is specified because it's the final param
    // Remember 9b, control click on downTo or step
    fun Int.isEven() = this % 2 == 0

    // forEach is defined as: fun <T> Iterable<T>.forEach(action: (T) -> Unit): Unit
    (1..5).forEach {
        println("$it isEven() = ${it.isEven()}")
    }
}