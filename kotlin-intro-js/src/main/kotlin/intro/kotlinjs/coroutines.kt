package intro.kotlinjs

import kotlin.coroutines.experimental.*
import kotlin.js.Promise

fun <T> async(block: suspend () -> T): Promise<T> = Promise<T> { resolve, reject ->
    block.startCoroutine(object : Continuation<T> {
        override val context: CoroutineContext get() = EmptyCoroutineContext
        override fun resume(value: T) {
            resolve(value)
        }

        override fun resumeWithException(exception: Throwable) {
            reject(exception)
        }
    })
}

suspend fun <T> Promise<T>.await() = suspendCoroutine<T> { cont ->
    then({ value -> cont.resume(value) },
            { exception -> cont.resumeWithException(exception) })
}