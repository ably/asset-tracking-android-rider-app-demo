package com.ably.tracking.demo.publisher.domain

import com.ably.tracking.demo.publisher.common.copyAndReplaceElementAt
import kotlinx.coroutines.flow.MutableStateFlow

class FakeOrderInteractor : OrderInteractor {

    override val orders: MutableStateFlow<List<Order>> = MutableStateFlow(emptyList())

    var trackedOrderId: String? = null

    override fun connect() {
        // no-op
    }

    override suspend fun assignOrder(
        orderId: String,
        destinationLatitude: Double,
        destinationLongitude: Double
    ) {
        orders.emit(orders.value.plus(Order(orderId, OrderState.Offline)))
    }

    suspend fun updateOrderState(orderId: String, orderState: OrderState) {
        val ordersList = orders.value
        val orderIndex = ordersList.indexOfFirst { it.id == orderId }
        val updatedOrder = ordersList[orderIndex].copy(state = orderState)

        orders.emit(
            ordersList.copyAndReplaceElementAt(orderIndex, updatedOrder)
        )
    }

    override suspend fun pickUpOrder(orderId: String) {
        trackedOrderId = orderId
    }

    override suspend fun removeOrder(orderId: String) {
        val order = orders.value.first { it.id == orderId }
        orders.emit(orders.value.minus(order))
    }

    override suspend fun disconnect() {
        // no-op
    }
}
