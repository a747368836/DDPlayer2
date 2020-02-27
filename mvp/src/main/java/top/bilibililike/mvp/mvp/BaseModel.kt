package top.bilibililike.mvp.mvp

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseModel {

    suspend fun <R> launchIO(block: suspend CoroutineScope.() -> R) = withContext(Dispatchers.IO) {
        block()
    }
}