package com.ably.tracking.demo.publisher.main

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.ably.tracking.demo.publisher.ably.AssetTracker
import com.ably.tracking.demo.publisher.common.BaseViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.*

class MainViewModel(
    private val context: Context,
    private val assetTracker: AssetTracker = AssetTracker.build(),
    coroutineScope: CoroutineDispatcher = Dispatchers.Main
) : BaseViewModel(coroutineScope) {

    private val clientId: String = UUID.randomUUID().toString()

    val state: MutableStateFlow<MainScreenState> = MutableStateFlow(MainScreenState())

    fun beginTracking() = viewModelScope.launch {
        assetTracker.connect(context, clientId)
    }

    fun finishTracking() = GlobalScope.launch {
        assetTracker.disconnect()
    }
}
