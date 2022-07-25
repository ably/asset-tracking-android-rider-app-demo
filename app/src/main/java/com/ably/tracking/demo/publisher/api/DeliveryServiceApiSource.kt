package com.ably.tracking.demo.publisher.api

interface DeliveryServiceApiSource {
    suspend fun getMapboxToken(authBase64: String): String

    suspend fun getAblyToken(authBase64: String): String
}
