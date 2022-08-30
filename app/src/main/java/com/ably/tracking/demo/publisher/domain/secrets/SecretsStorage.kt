package com.ably.tracking.demo.publisher.domain.secrets

interface SecretsStorage {
    fun readUsername(): String?
    fun writeUsername(value: String)

    fun readAuthorization(): String?
    fun writeAuthorization(value: String)
}
