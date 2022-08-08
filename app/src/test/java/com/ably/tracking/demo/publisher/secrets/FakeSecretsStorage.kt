package com.ably.tracking.demo.publisher.secrets

class FakeSecretsStorage : SecretsStorage {

    var username: String? = null
    var authorization: String? = null

    override fun readUsername(): String? = username

    override fun writeUsername(value: String) {
        username = value
    }

    override fun readAuthorization(): String? = authorization

    override fun writeAuthorization(value: String) {
        authorization = value
    }
}
