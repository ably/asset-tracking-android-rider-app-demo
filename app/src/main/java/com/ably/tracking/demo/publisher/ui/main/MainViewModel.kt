package com.ably.tracking.demo.publisher.ui.main

import androidx.annotation.StringRes
import com.ably.tracking.TrackableState
import com.ably.tracking.demo.publisher.R
import com.ably.tracking.demo.publisher.common.BaseViewModel
import com.ably.tracking.demo.publisher.domain.Order
import com.ably.tracking.demo.publisher.domain.OrderManager
import com.ably.tracking.demo.publisher.domain.OrderState
import com.ably.tracking.demo.publisher.ui.Navigator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainViewModel(
    private val orderManager: OrderManager,
    private val navigator: Navigator,
    coroutineScope: CoroutineDispatcher
) :
    BaseViewModel(coroutineScope) {

    val state: MutableStateFlow<MainScreenState> = MutableStateFlow(MainScreenState())

    private val trackableStates = mutableMapOf<String, StateFlow<TrackableState>>()

    init {
        launch {
            orderManager.orders
                .map { it.mapToViewItems() }
                .collect { orders ->
                    updateState {
                        it.copy(orders = orders)
                    }
                }
        }
    }

    private suspend fun updateState(update: (MainScreenState) -> MainScreenState) {
        state.emit(update(state.value))
    }

    private fun List<Order>.mapToViewItems() =
        map {
            OrderViewItem(
                name = it.id,
                state = it.state.toStringRes(),
                onTrackClicked = {
                    onTrackCLicked(it.id)
                },
                onRemoveClicked = {
                    onRemoveClicked(it.id)
                }
            )
        }

    @StringRes
    fun OrderState.toStringRes(): Int = when (this) {
        OrderState.Online -> R.string.trackable_state_online
        OrderState.Publishing -> R.string.trackable_state_publishing
        OrderState.Failed -> R.string.trackable_state_failed
        OrderState.Offline -> R.string.trackable_state_offline
    }

    fun onLocationPermissionGranted() = launch {
        orderManager.connect()
    }

    private fun onTrackCLicked(trackableId: String) = launch {
        orderManager.pickUpOrder(trackableId)
    }

    private fun onRemoveClicked(trackableId: String) = launch {
        orderManager.removeOrder(trackableId)
        trackableStates.remove(trackableId)
    }

    fun addOrder(orderName: String) =
        launch {
            orderManager.assignOrder(orderName)
        }

    fun onSettingsClicked() {
        navigator.openSettings()
    }
}
