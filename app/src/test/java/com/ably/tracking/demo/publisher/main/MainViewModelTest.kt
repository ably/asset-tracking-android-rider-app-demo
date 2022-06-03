package com.ably.tracking.demo.publisher.main

import com.ably.tracking.demo.publisher.BaseViewModelTest
import com.ably.tracking.demo.publisher.FakeAssetTracker
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
internal class MainViewModelTest : BaseViewModelTest() {

    private val assetTracker = FakeAssetTracker()

    private val viewModel = MainViewModel(assetTracker, baseTestCoroutineDispatcher)

    @Test
    fun `after calling add on view model new flow is created in asset tracker`() = runBlockingTest {
        //given
        val deliveryName = "Hawaii pizza"

        //when
        viewModel.addOrder(deliveryName)

        //then
        assertThat(viewModel.state.value.orders).contains(Order(deliveryName))
        assertThat(assetTracker.trackableStates).containsKey(deliveryName)
    }
}
