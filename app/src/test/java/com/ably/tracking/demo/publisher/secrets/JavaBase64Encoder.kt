package com.ably.tracking.demo.publisher.secrets

import com.ably.tracking.demo.publisher.domain.secrets.Base64Encoder
import java.util.Base64

class JavaBase64Encoder : Base64Encoder {
    override fun encode(input: String) =
        String(Base64.getEncoder().encode(input.toByteArray()))
}
