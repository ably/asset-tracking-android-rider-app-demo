package com.ably.tracking.demo.publisher.data.api

import com.google.gson.annotations.SerializedName

data class OrderResponse(
    @SerializedName("to") val destination: Destination
)

data class Destination(
    val latitude: Double,
    val longitude: Double
)
