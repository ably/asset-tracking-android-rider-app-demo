package com.ably.tracking.demo.publisher.secrets

import com.ably.tracking.demo.publisher.api.DeliveryServiceDataSource

class InMemorySecretsManager(
    private val deliveryServiceDataSource: DeliveryServiceDataSource,
    private val authBase64: String,
    private val authUsername: String
) : SecretsManager {

    companion object {
        private const val MAPBOX_TOKEN_KEY = "mapbox"
    }

    private val secrets = mutableMapOf<String, String>()

    override suspend fun loadSecrets(username: String, password: String) {
        secrets[MAPBOX_TOKEN_KEY] = deliveryServiceDataSource.getMapboxToken(authBase64)
    }

    override fun getUsername(): String = authUsername

    override fun getMapboxToken(): String = secrets[MAPBOX_TOKEN_KEY] ?: ""

    override suspend fun getAblyToken(): String = deliveryServiceDataSource.getAblyToken(authBase64)
}
