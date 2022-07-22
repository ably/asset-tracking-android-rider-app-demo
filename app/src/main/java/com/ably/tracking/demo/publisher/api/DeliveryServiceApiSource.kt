package com.ably.tracking.demo.publisher.api

class DeliveryServiceApiSource(private val deliveryServiceApi: DeliveryServiceApi) {

    companion object {
        private const val AUTHORIZATION_HEADER_PREFIX = "Basic "
    }

    suspend fun getMapboxToken(authBase64: String) =
        deliveryServiceApi.getMapboxToken(AUTHORIZATION_HEADER_PREFIX + authBase64).token

    suspend fun getAblyToken(authBase64: String) =
        deliveryServiceApi.getAblyToken(AUTHORIZATION_HEADER_PREFIX + authBase64).token

}
