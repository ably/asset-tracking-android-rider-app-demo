package com.ably.tracking.demo.publisher.api

import retrofit2.http.GET
import retrofit2.http.Header

interface DeliveryServiceApi {

    companion object {
        private const val AUTHORIZATION_HEADER_NAME = "Authorization"
    }

    @GET("mapbox")
    suspend fun getMapboxToken(@Header(AUTHORIZATION_HEADER_NAME) authorizationHeader: String): TokenResponse

    @GET("ably")
    suspend fun getAblyToken(@Header(AUTHORIZATION_HEADER_NAME) authorizationHeader: String): TokenResponse
}
