package com.ably.tracking.demo.publisher.ui.splash

import com.ably.tracking.demo.publisher.BaseViewModelTest
import com.ably.tracking.demo.publisher.secrets.FakeSecretsManager
import com.ably.tracking.demo.publisher.ui.FakeNavigator
import com.ably.tracking.demo.publisher.ui.main.MainActivity
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
internal class SplashViewModelTest : BaseViewModelTest() {

    private val navigator = FakeNavigator()

    private val secretsManager = FakeSecretsManager()

    private val viewModel = SplashViewModel(secretsManager, navigator, baseTestCoroutineDispatcher)

    @Test
    fun `after calling onContinueClicked view model navigates to main`() = runTest {
        // given

        // when
        viewModel.onContinueClicked()

        // then
        Truth.assertThat(navigator.navigationPath[0]).isEqualTo(MainActivity::class.java)
    }

    @Test
    fun `when fetching secrets throws exception show message`() = runTest {
        // given
        secretsManager.loadSecretsException = RuntimeException("loading secrets failed")

        // when
        viewModel.onContinueClicked()

        // then
        val state = viewModel.state.value
        Truth.assertThat(state.showFetchingSecretsFailedDialog).isEqualTo(true)
        Truth.assertThat(state.showProgress).isEqualTo(false)
    }

    @Test
    fun `after calling onUsernameChanged username in state is updated`() = runTest {
        // given


        // when
        viewModel.onUsernameChanged("rider")

        // then
        val state = viewModel.state.value
        Truth.assertThat(state.username).isEqualTo("rider")
    }

    @Test
    fun `after calling onPasswordChanged password in state is updated`() = runTest {
        // given


        // when
        viewModel.onPasswordChanged("password")

        // then
        val state = viewModel.state.value
        Truth.assertThat(state.password).isEqualTo("password")
    }
}
