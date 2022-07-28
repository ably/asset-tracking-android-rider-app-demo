package com.ably.tracking.demo.publisher.api

class FakeDeliveryServiceApiSource : DeliveryServiceApiSource {

    var mapboxToken: String = ""

    var ablyToken: String = ""

    override suspend fun getMapboxToken(authBase64: String): String = mapboxToken

    override suspend fun getAblyToken(authBase64: String): String = ablyToken

    override suspend fun deleteOrder(authBase64: String, orderId: Long) {
        // no-op
    }

    override suspend fun assignOrder(authBase64: String, orderId: Long) {
        // no-op
    }
}
