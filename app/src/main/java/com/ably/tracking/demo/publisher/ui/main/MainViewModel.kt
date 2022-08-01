package com.ably.tracking.demo.publisher.ui.main

import com.ably.tracking.TrackableState
import com.ably.tracking.demo.publisher.ably.AssetTracker
import com.ably.tracking.demo.publisher.common.BaseViewModel
import com.ably.tracking.demo.publisher.common.toStringRes
import com.ably.tracking.demo.publisher.ui.Navigator
import com.ably.tracking.publisher.Trackable
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel(
    private val assetTracker: AssetTracker,
    private val navigator: Navigator,
    coroutineScope: CoroutineDispatcher
) :
    BaseViewModel(coroutineScope) {

    val state: MutableStateFlow<MainScreenState> = MutableStateFlow(MainScreenState())

    private val trackableStates = mutableMapOf<String, StateFlow<TrackableState>>()

    fun onLocationPermissionGranted() = launch {
        assetTracker.connect()
            .map { it.mapToOrders() }
            .collect { trackables ->
                updateState {
                    it.copy(orders = trackables)
                }
            }
    }

    private fun Set<Trackable>.mapToOrders() =
        map {
            Order(
                name = it.id,
                state = it.getState().toStringRes(),
                onTrackClicked = {
                    onTrackCLicked(it.id)
                },
                onRemoveClicked = {
                    onRemoveClicked(it.id)
                }
            )
        }

    private fun Trackable.getState(): TrackableState {
        val trackableStateFlow = trackableStates[id] ?: assetTracker.getTrackableState(id)
        return trackableStateFlow?.value ?: TrackableState.Offline()
    }

    private fun onTrackCLicked(trackableId: String) = launch {
        assetTracker.track(trackableId)
    }

    private fun onRemoveClicked(trackableId: String) = launch {
        assetTracker.remove(trackableId)
        trackableStates.remove(trackableId)
    }

    fun addOrder(orderName: String, destinationLatitude: String, destinationLongitude: String) =
        launch {
            val trackableStateFlow = assetTracker.addTrackable(
                orderName,
                destinationLatitude.toDouble(),
                destinationLongitude.toDouble()
            )

            trackableStates[orderName] = trackableStateFlow

            trackableStateFlow
                .onEach { state ->
                    updateState { it.updateTrackableState(orderName, state) }
                }
                .launchIn(this)
        }

    private fun MainScreenState.updateTrackableState(
        orderName: String,
        state: TrackableState
    ): MainScreenState {
        val orderIndex = orders.indexOfFirst { order -> order.name == orderName }
        return if (orderIndex == -1) {
            this
        } else {
            val updatedOrder = orders[orderIndex].copy(state = state.toStringRes())
            copy(orders = orders.copyAndReplaceOrderAt(orderIndex, updatedOrder))
        }
    }

    private fun List<Order>.copyAndReplaceOrderAt(index: Int, order: Order) =
        subList(0, index) + order + subList(index, size - 1)

    private suspend fun updateState(update: (MainScreenState) -> MainScreenState) {
        state.emit(update(state.value))
    }

    fun onSettingsClicked() {
        navigator.openSettings()
    }
}
