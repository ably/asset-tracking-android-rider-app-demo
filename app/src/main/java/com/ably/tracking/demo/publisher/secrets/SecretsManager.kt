package com.ably.tracking.demo.publisher.secrets

interface SecretsManager {
    suspend fun loadSecrets()
    fun getUsername(): String
    fun getMapboxToken(): String
    fun getAblyToken(): String
}
