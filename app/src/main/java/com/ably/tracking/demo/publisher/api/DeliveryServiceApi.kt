package com.ably.tracking.demo.publisher.api

import com.google.gson.JsonObject
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface DeliveryServiceApi {

    companion object {
        private const val AUTHORIZATION_HEADER_NAME = "Authorization"
    }

    @GET("mapbox")
    suspend fun getMapboxToken(@Header(AUTHORIZATION_HEADER_NAME) authorizationHeader: String): TokenResponse

    @GET("ably")
    suspend fun getAblyToken(@Header(AUTHORIZATION_HEADER_NAME) authorizationHeader: String): TokenResponse

    @PUT("orders/{orderID}")
    suspend fun assignOrder(
        @Header(AUTHORIZATION_HEADER_NAME) authorizationHeader: String,
        @Path("orderID") orderId: Long
    )

    @DELETE("orders/{orderID}")
    suspend fun deleteOrder(
        @Header(AUTHORIZATION_HEADER_NAME) authorizationHeader: String,
        @Path("orderID") orderId: Long
    )
}
