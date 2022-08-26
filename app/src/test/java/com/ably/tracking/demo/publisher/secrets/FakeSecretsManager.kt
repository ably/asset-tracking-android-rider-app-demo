package com.ably.tracking.demo.publisher.secrets

import com.ably.tracking.demo.publisher.domain.secrets.SecretsManager

class FakeSecretsManager : SecretsManager {

    var mapboxTokenValue: String? = null

    var ablyTokenValue: String? = null

    var usernameValue: String? = null

    var authorizationHeaderValue: String? = null

    var loadSecretsException: Exception? = null

    override suspend fun loadSecrets(username: String, password: String?) {
        loadSecretsException?.let {
            throw it
        }
    }

    override fun hasAuthorizationSecrets(): Boolean =
        authorizationHeaderValue != null && usernameValue != null

    override fun getUsername() = usernameValue

    override fun getMapboxToken() = mapboxTokenValue!!

    override fun getAuthorizationHeader() = authorizationHeaderValue

    override suspend fun getAblyToken() = ablyTokenValue!!
}
