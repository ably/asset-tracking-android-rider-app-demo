package com.ably.tracking.demo.publisher.domain.order

data class Order(
    val id: String,
    val state: OrderState,
)

enum class OrderState {
    Online,
    Publishing,
    Failed,
    Offline
}
