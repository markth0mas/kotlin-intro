package intro.kotlin

// Restricted class hierarchies
// All subclasses of a sealed class must be declared in the same file
sealed class Expr
data class Const(val number: Double) : Expr()
data class Sum(val e1: Expr, val e2: Expr) : Expr()

// The compiler will check that we've covered all of the cases
fun eval(expr: Expr): Double = when (expr) {
    is Const -> expr.number
    is Sum -> eval(expr.e1) + eval(expr.e2)
}

// You can still use enums
enum class Direction {
    NORTH, SOUTH, WEST, EAST
}

fun main(args: Array<String>) {
    println(eval(Const(1.0)))  // 1.0
    println(eval(Sum(Const(1.0), Const(2.0))))  // 3.0
}