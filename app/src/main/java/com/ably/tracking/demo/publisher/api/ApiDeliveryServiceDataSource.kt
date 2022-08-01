package com.ably.tracking.demo.publisher.api

class ApiDeliveryServiceDataSource(private val deliveryServiceApi: DeliveryServiceApi) :
    DeliveryServiceDataSource {

    companion object {
        private const val AUTHORIZATION_HEADER_PREFIX = "Basic "
    }

    override suspend fun getMapboxToken(authBase64: String) =
        deliveryServiceApi.getMapboxToken(AUTHORIZATION_HEADER_PREFIX + authBase64).token

    override suspend fun getAblyToken(authBase64: String) =
        deliveryServiceApi.getAblyToken(AUTHORIZATION_HEADER_PREFIX + authBase64).token
}