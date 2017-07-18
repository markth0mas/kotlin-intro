package intro.kotlin

fun factorial(n: Int) = (1..n).fold(1, Int::times)

fun factorial2(n: Int): Int =
        when (n) {
            0 -> 1
            else -> n * factorial2(n - 1)
        }
