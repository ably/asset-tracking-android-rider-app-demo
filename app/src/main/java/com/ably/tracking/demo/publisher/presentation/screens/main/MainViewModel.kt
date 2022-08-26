package com.ably.tracking.demo.publisher.presentation.screens.main

import androidx.annotation.StringRes
import com.ably.tracking.demo.publisher.R
import com.ably.tracking.demo.publisher.presentation.BaseViewModel
import com.ably.tracking.demo.publisher.domain.order.Order
import com.ably.tracking.demo.publisher.domain.order.OrderManager
import com.ably.tracking.demo.publisher.domain.order.OrderState
import com.ably.tracking.demo.publisher.presentation.navigation.Navigator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainViewModel(
    private val orderManager: OrderManager,
    private val navigator: Navigator,
    coroutineScope: CoroutineDispatcher
) :
    BaseViewModel(coroutineScope) {

    val state: MutableStateFlow<MainScreenState> = MutableStateFlow(MainScreenState())

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
        OrderState.Online -> R.string.order_state_online
        OrderState.Publishing -> R.string.order_state_publishing
        OrderState.Failed -> R.string.order_state_failed
        OrderState.Offline -> R.string.order_state_offline
    }

    fun onLocationPermissionGranted() = launch {
        orderManager.connect()
    }

    private fun onTrackCLicked(orderID: String) = launch {
        orderManager.pickUpOrder(orderID)
    }

    private fun onRemoveClicked(orderID: String) = launch {
        orderManager.removeOrder(orderID)
    }

    fun addOrder(orderName: String) =
        launch {
            orderManager.assignOrder(orderName)
        }

    fun onSettingsClicked() {
        navigator.openSettings()
    }
}
