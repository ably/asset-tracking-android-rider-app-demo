package com.ably.tracking.demo.publisher.domain.secrets

interface Base64Encoder {
    fun encode(input: String): String
}
