package com.ably.tracking.demo.publisher.domain

import com.ably.tracking.TrackableState
import com.ably.tracking.demo.publisher.ably.AssetTracker
import com.ably.tracking.demo.publisher.api.DeliveryServiceDataSource
import com.ably.tracking.demo.publisher.common.copyAndReplaceElementAt
import com.ably.tracking.demo.publisher.common.toOrderState
import com.ably.tracking.demo.publisher.secrets.SecretsManager
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

interface OrderManager {
    val orders: StateFlow<List<Order>>

    fun connect()

    suspend fun assignOrder(
        orderId: String,
        destinationLatitude: Double,
        destinationLongitude: Double
    )

    suspend fun pickUpOrder(orderId: String)

    suspend fun removeOrder(orderId: String)

    suspend fun disconnect()
}

class DefaultOrderInteractor(
    private val assetTracker: AssetTracker,
    private val deliveryServiceDataSource: DeliveryServiceDataSource,
    private val secretsManager: SecretsManager,
    coroutineDispatcher: CoroutineDispatcher
) : OrderManager, CoroutineScope {

    override val coroutineContext: CoroutineContext = SupervisorJob() + coroutineDispatcher

    private val trackableStateFlows = mutableMapOf<String, StateFlow<TrackableState>>()

    override val orders: MutableStateFlow<List<Order>> = MutableStateFlow(emptyList())

    override fun connect() {
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

    override suspend fun assignOrder(
        orderId: String,
        destinationLatitude: Double,
        destinationLongitude: Double
    ) {
        deliveryServiceDataSource.assignOrder(secretsManager.getAuthorizationHeader()!!, orderId.toLong())
        addOrder(orderId, destinationLatitude, destinationLongitude)
    }

    private suspend fun addOrder(
        orderId: String,
        destinationLatitude: Double,
        destinationLongitude: Double
    ) {
        val trackableStateFlow =
            assetTracker.addTrackable(orderId, destinationLatitude, destinationLongitude)

        trackableStateFlows[orderId] = trackableStateFlow

        trackableStateFlow
            .onEach { state ->
                updateOrders { it.updateOrders(orderId, state) }
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
            copyAndReplaceElementAt(orderIndex, updatedOrder)
        }
    }

    private suspend fun updateOrders(update: (List<Order>) -> List<Order>) {
        orders.emit(update(orders.value))
    }

    private fun getTrackableState(trackableId: String): StateFlow<TrackableState>? =
        assetTracker.getTrackableState(trackableId)

    override suspend fun pickUpOrder(orderId: String) {
        assetTracker.track(orderId)
    }

    override suspend fun removeOrder(orderId: String) {
        assetTracker.remove(orderId)
        trackableStateFlows.remove(orderId)
    }

    override suspend fun disconnect() = assetTracker.disconnect()
}
