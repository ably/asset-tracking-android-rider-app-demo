package com.ably.tracking.demo.publisher.common

import androidx.lifecycle.ViewModel
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren

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
