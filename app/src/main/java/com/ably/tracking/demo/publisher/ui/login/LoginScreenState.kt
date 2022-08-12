package com.ably.tracking.demo.publisher.ui.login

data class LoginScreenState(
    val username: String = "",
    val password: String = "",
    val showFetchingSecretsFailedDialog: Boolean = false,
    val showProgress: Boolean = false
)
