package com.ably.tracking.demo.publisher.presentation.screens.main

import androidx.annotation.StringRes

data class OrderViewItem(
    val name: String,
    @StringRes val state: Int,
    val onTrackClicked: () -> Unit,
    val onRemoveClicked: () -> Unit
)
