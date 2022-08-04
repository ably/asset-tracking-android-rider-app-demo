package com.ably.tracking.demo.publisher.domain

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
