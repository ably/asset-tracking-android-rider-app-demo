package com.ably.tracking.demo.publisher.secrets

class FakeSecretsManager : SecretsManager {

    var mapboxTokenValue: String? = null

    var ablyTokenValue: String? = null

    var usernameValue: String? = null

    var loadSecretsException: Exception? = null

    override suspend fun loadSecrets() {
        loadSecretsException?.let {
            throw it
        }
    }

    override fun getUsername() = usernameValue!!

    override fun getMapboxToken() = mapboxTokenValue!!

    override suspend fun getAblyToken() = ablyTokenValue!!
}
