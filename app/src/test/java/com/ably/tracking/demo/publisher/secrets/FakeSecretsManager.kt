package com.ably.tracking.demo.publisher.secrets

class FakeSecretsManager : SecretsManager {

    var mapboxTokenValue: String? = null

    var ablyTokenValue: String? = null

    override suspend fun loadSecrets() {
        //no-op
    }

    override fun getMapboxToken() = mapboxTokenValue

    override fun getAblyToken() = ablyTokenValue
}
