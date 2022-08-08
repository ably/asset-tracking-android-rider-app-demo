package com.ably.tracking.demo.publisher.ui.splash

data class SplashScreenState(
    val username: String = "",
    val password: String = "",
    val showFetchingSecretsFailedDialog: Boolean = false,
    val showProgress: Boolean = false
)
