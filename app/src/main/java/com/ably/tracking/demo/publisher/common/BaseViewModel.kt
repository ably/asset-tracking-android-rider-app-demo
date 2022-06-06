package com.ably.tracking.demo.publisher.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(private val baseCoroutineDispatcher: CoroutineDispatcher) :
    ViewModel(),
    CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = baseCoroutineDispatcher + viewModelJob

    private val viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancelChildren()
    }
}
