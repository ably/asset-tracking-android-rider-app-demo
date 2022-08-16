package com.ably.tracking.demo.publisher.api

class FakeDeliveryServiceDataSource : DeliveryServiceDataSource {

    var lastAuthorizationHeader: String? = null
        private set

    var mapboxToken: String = ""

    var ablyToken: String = ""

    override suspend fun getMapboxToken(authBase64: String): String {
        lastAuthorizationHeader = authBase64
        return mapboxToken
    }

    override suspend fun getAblyToken(authBase64: String): String {
        lastAuthorizationHeader = authBase64
        return ablyToken
    }

    override suspend fun assignOrder(authBase64: String, orderId: Long): Destination {
        lastAuthorizationHeader = authBase64
        return Destination(0.0, 0.0)
    }

    override suspend fun deleteOrder(authBase64: String, orderId: Long) {
        lastAuthorizationHeader = authBase64
    }
}
