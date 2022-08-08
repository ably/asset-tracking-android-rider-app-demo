package com.ably.tracking.demo.publisher.secrets

interface SecretsManager {
    suspend fun loadSecrets(username: String, password: String)
    fun getUsername(): String
    fun getMapboxToken(): String
    suspend fun getAblyToken(): String
}
