package com.ably.tracking.demo.publisher.main

import androidx.annotation.StringRes

data class Order(
    val name: String,
    @StringRes val state: Int,
    val onTrackClicked: ()->Unit
)
