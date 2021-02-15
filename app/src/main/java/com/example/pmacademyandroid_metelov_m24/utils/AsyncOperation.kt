package com.example.pmacademyandroid_metelov_m24.utils

import android.os.Handler

class AsyncOperation<T>(
    private val operation: () -> T,
    private val mainHandler: Handler,
    private val threadCreation: (Runnable) -> Thread
) {
    fun postOnMainThread(callback: (T) -> Unit): CancelableOperation {
        val activeThread = threadCreation {
            try {
                val result = operation()
                if (!Thread.currentThread().isInterrupted) {
                    mainHandler.post {
                        callback(result)
                    }
                }
            } catch (ignore: Exception) {
            }
        }

        return CancelableOperation {
            activeThread.interrupt()
        }
    }

    fun <R> map(transformation: (T) -> R): AsyncOperation<R> {
        return AsyncOperation(
            operation = { transformation(operation()) },
            mainHandler = mainHandler,
            threadCreation = threadCreation
        )
    }

}