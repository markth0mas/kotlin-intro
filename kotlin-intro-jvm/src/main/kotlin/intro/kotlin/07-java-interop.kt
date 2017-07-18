package intro.kotlin

fun main(args: Array<String>) {
    // 7b. Kotlin relaxes the rules for platform types (Java types)
    val javaClass = JavaClass()
    val message: String? = javaClass.returnStringNull()

    // Check out optional deference
    println(message?.length)  // Prints null

    // This causes an IllegalStateException at runtime
    // Kotlin assumes type is (String! - could be String or String?, check before dereferencing)
    // You can override this behaviour by annotating @NotNull or @Nullable
    //    val message = javaClass.returnStringNull()
    //    println(message.length)
}