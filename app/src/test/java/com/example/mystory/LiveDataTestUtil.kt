package com.example.mystory

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
@VisibleForTesting(otherwise = VisibleForTesting.NONE)
suspend fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 5,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    afterObserve: suspend () -> Unit = {}
): T {
    val data = runBlocking {
        withTimeoutOrNull(timeUnit.toMillis(time)) {
            val latch = CountDownLatch(1)
            var value: T? = null

            val observer = object : Observer<T> {
                override fun onChanged(newValue: T) {
                    value = newValue
                    latch.countDown()
                    this@getOrAwaitValue.removeObserver(this)
                }
            }

            this@getOrAwaitValue.observeForever(observer)
            try {
                afterObserve.invoke()
                latch.await()
                value
            } finally {
                this@getOrAwaitValue.removeObserver(observer)
            }
        }
    }

    return requireNotNull(data) { "LiveData value was never set!" }
}
