package com.ably.tracking.demo.publisher.api

interface DeliveryServiceDataSource {
    suspend fun getMapboxToken(authBase64: String): String

    suspend fun getAblyToken(authBase64: String): String

    suspend fun deleteOrder(authBase64: String, orderId: Long)

    suspend fun assignOrder(authBase64: String, orderId: Long)
}