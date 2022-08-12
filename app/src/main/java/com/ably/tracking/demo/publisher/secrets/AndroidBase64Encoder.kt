package com.ably.tracking.demo.publisher.secrets

import android.util.Base64

class AndroidBase64Encoder : Base64Encoder {
    override fun encode(input: String) =
        //dropping new line appended on the end by the encoder
        Base64.encodeToString(input.toByteArray(), Base64.DEFAULT).dropLast(1)
}
