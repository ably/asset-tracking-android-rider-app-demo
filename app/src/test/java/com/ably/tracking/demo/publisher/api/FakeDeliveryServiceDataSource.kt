package com.ably.tracking.demo.publisher.api

class FakeDeliveryServiceDataSource : DeliveryServiceDataSource {

    var mapboxToken: String = ""

    var ablyToken: String = ""

    override suspend fun getMapboxToken(authBase64: String): String = mapboxToken

    override suspend fun getAblyToken(authBase64: String): String = ablyToken
}
