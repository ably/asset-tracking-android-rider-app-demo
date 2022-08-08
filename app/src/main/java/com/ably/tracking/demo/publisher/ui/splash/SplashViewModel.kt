package com.ably.tracking.demo.publisher.ui.splash

import com.ably.tracking.demo.publisher.common.BaseViewModel
import com.ably.tracking.demo.publisher.secrets.SecretsManager
import com.ably.tracking.demo.publisher.ui.Navigator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val secretsManager: SecretsManager,
    private val navigator: Navigator,
    coroutineScope: CoroutineDispatcher
) :
    BaseViewModel(coroutineScope) {

    val state: MutableStateFlow<SplashScreenState> = MutableStateFlow(SplashScreenState())

    fun onCreate() = launch {
        tryLoadSecrets()
    }

    private suspend fun tryLoadSecrets() {
        try {
            secretsManager.loadSecrets()
            navigator.openMain()
            navigator.closeCurrentScreen()
        } catch (_: Exception) {
            state.emit(state.value.copy(showFetchingSecretsFailedDialog = true))
        }
    }

    fun onFetchingSecretsFailedDialogClosed() {
        navigator.closeCurrentScreen()
    }

    fun onUsernameChanged(value: String) {

    }

    fun onPasswordChanged(value: String) {

    }

    fun onContinueClicked() {

    }
}
