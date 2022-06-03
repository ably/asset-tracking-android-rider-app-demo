package com.ably.tracking.demo.publisher.main

import com.ably.tracking.demo.publisher.ably.AssetTracker
import com.ably.tracking.demo.publisher.common.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel(private val assetTracker: AssetTracker, coroutineScope: CoroutineDispatcher) :
    BaseViewModel(coroutineScope) {

    private val clientId: String = UUID.randomUUID().toString()

    val state: MutableStateFlow<MainScreenState> = MutableStateFlow(MainScreenState())

    fun beginTracking() = launch {
        assetTracker.connect(clientId)
    }

    fun finishTracking() = GlobalScope.launch {
        assetTracker.disconnect()
    }

    fun addOrder(deliveryName: String) = launch {
        assetTracker.addTrackable(deliveryName)
        val order = Order(deliveryName)
        updateState { state ->
            state.copy(orders = state.orders + order)
        }
    }

    private suspend fun updateState(update: (MainScreenState) -> MainScreenState) {
        state.emit(update(state.value))
    }
}
