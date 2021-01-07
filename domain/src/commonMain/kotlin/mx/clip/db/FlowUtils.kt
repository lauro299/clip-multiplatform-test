package mx.clip.db

import io.ktor.utils.io.core.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.*

fun <T> Flow<T>.wrap():CFlow<T> = CFlow(this)

class CFlow<T>(private val origin:Flow<T>): Flow<T> by origin{
    fun watch(block:(T)->Unit): Closeable{
        val job = Job(/**/)
        onEach{
            block(it)
        }.launchIn(CoroutineScope(Dispatchers.Main + job))
        return object: Closeable{
            override fun close(){
                job.cancel()
            }
        }
    }
}
    fun <T> toCFlow(flow:Flow<T>):CFlow<T>{
        return flow.wrap()
    }
