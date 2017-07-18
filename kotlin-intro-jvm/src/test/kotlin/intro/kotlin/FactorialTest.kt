package intro.kotlin

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class FactorialBehaviour {
    @Test
    fun `factorial(0) = 1`() {
        assert.that(factorial(0), equalTo(1))
        assert.that(factorial2(0), equalTo(1))
    }

    @Test
    fun `factorial(1) = 1`() {
        assert.that(factorial(1), equalTo(1))
        assert.that(factorial2(1), equalTo(1))
    }

    @Test
    fun `factorial(5) = 120`() {
        assert.that(factorial(5), equalTo(120))
        assert.that(factorial2(5), equalTo(120))
    }
}