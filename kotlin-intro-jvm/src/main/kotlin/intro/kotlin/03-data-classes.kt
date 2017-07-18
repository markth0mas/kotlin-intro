package intro.kotlin

// Shortcuts for value types (types where identity is defined by the value of the fields)
// Readers exercise: compare to Immutables
data class VideoGame(
        val name: String,
        val publisher: String,
        val yearOfRelease: Int,
        val rating: Int? = null
)

fun main(args: Array<String>) {
    // Default rating
    val mario = VideoGame("Super Mario Bros.", "Nintendo", 1985)
    val marioWithRating = mario.copy(rating = 5)

    // Mixing up the arguments by naming them
    val mario64 = VideoGame(publisher = "Nintendo", name = "Super Mario 64", yearOfRelease = 1996)

    // Sensible toString
    // VideoGame(name=Super Mario 64, publisher=Nintendo, yearOfRelease=1996, rating=null)
    println(mario64)

    // Equality works as you'd expect
    println(mario == mario.copy())    // true
    println(mario == marioWithRating) // false

    // Destructuring (implemented by generating .componentN() method)
    val games = listOf(marioWithRating, mario64)

    for ((name) in games) {
        println(name)
    }
}