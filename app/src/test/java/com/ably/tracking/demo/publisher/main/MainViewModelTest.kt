package com.ably.tracking.demo.publisher.main

import com.ably.tracking.TrackableState
import com.ably.tracking.demo.publisher.BaseViewModelTest
import com.ably.tracking.demo.publisher.FakeAssetTracker
import com.ably.tracking.demo.publisher.R
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
internal class MainViewModelTest : BaseViewModelTest() {

    private val destinationLatitude = "51.1065859"
    private val destinationLongitude = "17.0312766"

    private val assetTracker = FakeAssetTracker()

    private val viewModel = MainViewModel(assetTracker, baseTestCoroutineDispatcher)

    @Test
    fun `after calling add on view model new order is created`() = runBlockingTest {
        //given
        val orderName = "Hawaii pizza"

        //when
        viewModel.addOrder(orderName, destinationLatitude, destinationLongitude)

        //then
        val order = viewModel.state.value.orders.firstOrNull { it.name == orderName }
        assertThat(order).isNotNull()
        assertThat(order!!.state).isEqualTo(R.string.trackable_state_offline)
    }

    @Test
    fun `after updating order state in asset manager view model state is updated`() =
        runBlockingTest {
            //given
            val orderName = "Sushi"
            viewModel.addOrder(orderName, destinationLatitude, destinationLongitude)

            //when
            assetTracker.trackableStates[orderName]?.emit(TrackableState.Publishing)

            //then
            val order = viewModel.state.value.orders.first { it.name == orderName }
            assertThat(order.state).isEqualTo(R.string.trackable_state_publishing)
        }

    @Test
    fun `onTrack clicked selected trackable is tracked`() =
        runBlockingTest {
            //given
            val orderName = "Pancake"
            viewModel.addOrder(orderName, destinationLatitude, destinationLongitude)

            //when
            viewModel.state.value.orders.first().onTrackClicked()

            //then
            assertThat(assetTracker.trackedTrackableId).isEqualTo(orderName)
        }
}