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

    fun onCreated() = launch {
        val username = secretsManager.getUsername() ?: ""
        updateState { it.copy(username = username) }

        if (secretsManager.hasAuthorizationSecrets()) {
            tryLoadSecrets(username, null)
        }
    }

    fun onFetchingSecretsFailedDialogClosed() = launch {
        updateState { it.copy(showFetchingSecretsFailedDialog = false) }
    }

    fun onUsernameChanged(value: String) = launch {
        updateState {
            it.copy(username = value)
        }
    }

    fun onPasswordChanged(value: String) = launch {
        updateState {
            it.copy(password = value)
        }
    }

    fun onContinueClicked() = launch {
        val stateValue = state.value
        tryLoadSecrets(stateValue.username, stateValue.password)
    }

    private suspend fun tryLoadSecrets(username: String, password: String?) {
        updateState { it.copy(showProgress = true) }
        try {
            secretsManager.loadSecrets(username, password)
            navigator.openMain()
            navigator.closeCurrentScreen()
        } catch (_: Exception) {
            updateState { it.copy(showFetchingSecretsFailedDialog = true) }
        } finally {
            updateState { it.copy(showProgress = false) }
        }
    }

    private suspend fun updateState(update: (SplashScreenState) -> SplashScreenState) {
        state.emit(update(state.value))
    }
}
