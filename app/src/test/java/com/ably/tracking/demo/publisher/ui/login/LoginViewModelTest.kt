package com.ably.tracking.demo.publisher.ui.login

import com.ably.tracking.demo.publisher.BaseViewModelTest
import com.ably.tracking.demo.publisher.presentation.screens.login.LoginViewModel
import com.ably.tracking.demo.publisher.secrets.FakeSecretsManager
import com.ably.tracking.demo.publisher.ui.FakeNavigator
import com.ably.tracking.demo.publisher.presentation.navigation.Routes
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
internal class LoginViewModelTest : BaseViewModelTest() {

    private val navigator = FakeNavigator()

    private val secretsManager = FakeSecretsManager()

    private val viewModel = LoginViewModel(secretsManager, navigator, baseTestCoroutineDispatcher)

    @Test
    fun `after calling onCreated username is read from secretsManager`() = runTest {
        // given
        secretsManager.usernameValue = "rider"

        // when
        viewModel.onCreated()

        // then
        val state = viewModel.state.value
        assertThat(state.username).isEqualTo("rider")
    }

    @Test
    fun `after calling onCreated attempts to load secret and navigate if has credentials`() =
        runTest {
            // given
            secretsManager.usernameValue = "rider"
            secretsManager.authorizationHeaderValue = "cmlkZXI6cGFzc3dvcmQ"

            // when
            viewModel.onCreated()

            // then
            assertThat(navigator.navigationPath[0]).isEqualTo(Routes.Main.path)
        }

    @Test
    fun `after calling onContinueClicked view model navigates to main`() = runTest {
        // given

        // when
        viewModel.onContinueClicked()

        // then
        assertThat(navigator.navigationPath[0]).isEqualTo(Routes.Main.path)
    }

    @Test
    fun `when fetching secrets throws exception show message`() = runTest {
        // given
        secretsManager.loadSecretsException = RuntimeException("loading secrets failed")

        // when
        viewModel.onContinueClicked()

        // then
        val state = viewModel.state.value
        assertThat(state.showFetchingSecretsFailedDialog).isEqualTo(true)
        assertThat(state.showProgress).isEqualTo(false)
    }

    @Test
    fun `after calling onUsernameChanged username in state is updated`() = runTest {
        // given

        // when
        viewModel.onUsernameChanged("rider")

        // then
        val state = viewModel.state.value
        assertThat(state.username).isEqualTo("rider")
    }

    @Test
    fun `after calling onPasswordChanged password in state is updated`() = runTest {
        // given

        // when
        viewModel.onPasswordChanged("password")

        // then
        val state = viewModel.state.value
        assertThat(state.password).isEqualTo("password")
    }
}
