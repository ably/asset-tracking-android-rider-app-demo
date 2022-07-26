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

    private val destinationLatitude = "51.1065859"
    private val destinationLongitude = "17.0312766"

    private val navigator = FakeNavigator()

    private val secretsManager = FakeSecretsManager()

    private val viewModel = SplashViewModel(secretsManager, navigator, baseTestCoroutineDispatcher)

    @Test
    fun `after calling add on view model new order is created`() = runTest {
        // given

        // when
        viewModel.onCreate()

        // then
        Truth.assertThat(navigator.navigationPath[0]).isEqualTo(MainActivity::class.java)
    }
}
