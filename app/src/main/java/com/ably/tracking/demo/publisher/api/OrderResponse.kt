package com.ably.tracking.demo.publisher.api

data class OrderResponse(
    val to: Destination
)

data class Destination(
    val latitude: Double,
    val longitude: Double
)
