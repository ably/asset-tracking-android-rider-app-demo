package com.ably.tracking.demo.publisher.api

import retrofit2.http.GET
import retrofit2.http.Header

interface DeliveryServiceApi {
    @GET("mapbox")
    suspend fun getMapboxToken(@Header("Authorization") authorizationHeader: String): TokenResponse

    @GET("ably")
    suspend fun getAblyToken(@Header("Authorization") authorizationHeader: String): TokenResponse
}
