package com.ably.tracking.demo.publisher.secrets

import com.ably.tracking.demo.publisher.api.DeliveryServiceApiSource

class InMemorySecretsManager(
    private val deliveryServiceApiSource: DeliveryServiceApiSource,
    private val authBase64: String,
    private val authUsername: String
) : SecretsManager {

    companion object {
        private const val MAPBOX_TOKEN_KEY = "mapbox"
        private const val ALBY_TOKEN_KEY = "ably"
    }

    private val secrets = mutableMapOf<String, String>()

    override suspend fun loadSecrets() {
        secrets[MAPBOX_TOKEN_KEY] = deliveryServiceApiSource.getMapboxToken(authBase64)
        secrets[ALBY_TOKEN_KEY] = deliveryServiceApiSource.getAblyToken(authBase64)
    }

    override fun getUsername(): String = authUsername

    override fun getMapboxToken(): String = secrets[MAPBOX_TOKEN_KEY] ?: ""

    override fun getAblyToken(): String = secrets[ALBY_TOKEN_KEY] ?: ""
}
