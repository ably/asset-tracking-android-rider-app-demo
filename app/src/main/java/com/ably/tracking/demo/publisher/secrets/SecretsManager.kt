package com.ably.tracking.demo.publisher.secrets

interface SecretsManager {
    suspend fun loadSecrets()
    fun getMapboxToken(): String?
    fun getAblyToken(): String?
}
