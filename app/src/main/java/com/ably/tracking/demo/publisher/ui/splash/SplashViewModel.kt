package com.ably.tracking.demo.publisher.ui.splash

import com.ably.tracking.demo.publisher.common.BaseViewModel
import com.ably.tracking.demo.publisher.secrets.SecretsManager
import com.ably.tracking.demo.publisher.ui.Navigator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class SplashViewModel(
    private val secretsManager: SecretsManager,
    private val navigator: Navigator,
    coroutineScope: CoroutineDispatcher
) :
    BaseViewModel(coroutineScope) {

    fun onCreate() {
        launch {
            secretsManager.loadSecrets()
            navigator.openMain()
        }
    }

}
