package com.ably.tracking.demo.publisher.main

import com.ably.tracking.TrackableState
import com.ably.tracking.demo.publisher.ably.AssetTracker
import com.ably.tracking.demo.publisher.common.BaseViewModel
import com.ably.tracking.demo.publisher.common.toStringRes
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

    fun addOrder(orderName: String, destinationLatitude: String, destinationLongitude: String) =
        launch {
            val trackableStateFlow = assetTracker.addTrackable(orderName)

            val order = Order(orderName, trackableStateFlow.value.toStringRes()) {
                onTrackCLicked(orderName)
            }
            updateState { state ->
                state.copy(orders = state.orders + order)
            }

            trackableStateFlow
                .onEach { state ->
                    updateState { it.updateTrackableState(orderName, state) }
                }
                .launchIn(this)
        }

    private fun onTrackCLicked(trackableId: String) = launch {
        assetTracker.track(trackableId)
    }

    private fun MainScreenState.updateTrackableState(
        orderName: String,
        state: TrackableState
    ): MainScreenState {
        val orderIndex = orders.indexOfFirst { order -> order.name == orderName }
        val updatedOrder = orders[orderIndex].copy(state = state.toStringRes())
        return copy(orders = orders.copyAndReplaceOrderAt(orderIndex, updatedOrder))
    }

    private fun List<Order>.copyAndReplaceOrderAt(index: Int, order: Order) =
        subList(0, index) + order + subList(index, size - 1)

    private suspend fun updateState(update: (MainScreenState) -> MainScreenState) {
        state.emit(update(state.value))
    }
}
