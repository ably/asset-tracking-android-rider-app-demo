package com.ably.tracking.demo.publisher.domain

import com.ably.tracking.TrackableState
import com.ably.tracking.demo.publisher.ably.AssetTracker
import com.ably.tracking.demo.publisher.api.DeliveryServiceDataSource
import com.ably.tracking.demo.publisher.common.toOrderState
import com.ably.tracking.publisher.Trackable
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class OrderInteractor(
    private val assetTracker: AssetTracker,
    private val deliveryServiceDataSource: DeliveryServiceDataSource,
    private val authorizationHeaderBase64: String,
    coroutineDispatcher: CoroutineDispatcher
) : CoroutineScope {

    override val coroutineContext: CoroutineContext = SupervisorJob() + coroutineDispatcher

    private val trackableStateFlows = mutableMapOf<String, StateFlow<TrackableState>>()

    val orders: MutableStateFlow<List<Order>> = MutableStateFlow(emptyList())

    fun connect() {
        launch {
            assetTracker.connect().map { it.mapToOrders() }
                .collect { trackables ->
                    updateOrders { trackables }
                }
        }
    }

    private fun Set<Trackable>.mapToOrders() =
        map {
            Order(
                id = it.id,
                state = it.getState().toOrderState(),
            )
        }


    private fun Trackable.getState(): TrackableState {
        val trackableStateFlow = trackableStateFlows[id] ?: getTrackableState(id)
        return trackableStateFlow?.value ?: TrackableState.Offline()
    }

    suspend fun addTrackable(
        trackableId: String,
        destinationLatitude: Double,
        destinationLongitude: Double
    ) {
        deliveryServiceDataSource.assignOrder(authorizationHeaderBase64, trackableId.toLong())
        addOrder(trackableId, destinationLatitude, destinationLongitude)
    }

    private suspend fun addOrder(
        trackableId: String,
        destinationLatitude: Double,
        destinationLongitude: Double
    ) {
        val trackableStateFlow =
            assetTracker.addTrackable(trackableId, destinationLatitude, destinationLongitude)

        trackableStateFlows[trackableId] = trackableStateFlow

        trackableStateFlow
            .onEach { state ->
                updateOrders { it.updateOrders(trackableId, state) }
            }
            .launchIn(this)
    }

    private fun List<Order>.updateOrders(
        orderId: String,
        state: TrackableState
    ): List<Order> {
        val orderIndex = indexOfFirst { order -> order.id == orderId }
        return if (orderIndex == -1) {
            this
        } else {
            val updatedOrder = this[orderIndex].copy(state = state.toOrderState())
            copyAndReplaceOrderAt(orderIndex, updatedOrder)
        }
    }

    private fun List<Order>.copyAndReplaceOrderAt(index: Int, order: Order) =
        subList(0, index) + order + subList(index, size - 1)

    private suspend fun updateOrders(update: (List<Order>) -> List<Order>) {
        orders.emit(update(orders.value))
    }

    private fun getTrackableState(trackableId: String): StateFlow<TrackableState>? =
        assetTracker.getTrackableState(trackableId)

    suspend fun track(trackableId: String) = assetTracker.track(trackableId)

    suspend fun remove(trackableId: String) {
        assetTracker.remove(trackableId)
        trackableStateFlows.remove(trackableId)
    }

    suspend fun disconnect() = assetTracker.disconnect()
}
