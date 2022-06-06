package com.ably.tracking.demo.publisher

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.After
import org.junit.Before

@ExperimentalCoroutinesApi
open class BaseViewModelTest {

    val baseTestCoroutineDispatcher = TestCoroutineDispatcher()

    val baseTestCoroutineScope = CoroutineScope(baseTestCoroutineDispatcher + Job())

    private val oldDefaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()

    @Before
    fun setUncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler { _, throwable ->
            throw throwable
        }
    }

    @After
    fun resetUncaughtExceptionHandler() {
        baseTestCoroutineDispatcher.cleanupTestCoroutines()
        Thread.setDefaultUncaughtExceptionHandler(oldDefaultUncaughtExceptionHandler)
    }

    @After
    fun cleanupCoroutines() {
        baseTestCoroutineDispatcher.cleanupTestCoroutines()
    }
}
