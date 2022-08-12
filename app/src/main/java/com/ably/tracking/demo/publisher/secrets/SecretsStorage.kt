package com.ably.tracking.demo.publisher.secrets

interface SecretsStorage {
    fun readUsername(): String?
    fun writeUsername(value: String)

    fun readAuthorization(): String?
    fun writeAuthorization(value: String)
}
