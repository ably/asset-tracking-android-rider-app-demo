package com.ably.tracking.demo.publisher.domain

import com.ably.tracking.demo.publisher.data.api.Destination

interface DeliveryServiceDataSource {
    suspend fun getMapboxToken(authBase64: String): String

    suspend fun getAblyToken(authBase64: String): String

    suspend fun assignOrder(authBase64: String, orderId: Long): Destination

    suspend fun deleteOrder(authBase64: String, orderId: Long)
}
