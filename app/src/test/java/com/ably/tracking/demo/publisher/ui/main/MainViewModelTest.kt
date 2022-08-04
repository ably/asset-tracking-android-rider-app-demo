package com.ably.tracking.demo.publisher.ui.main

import com.ably.tracking.demo.publisher.BaseViewModelTest
import com.ably.tracking.demo.publisher.R
import com.ably.tracking.demo.publisher.domain.FakeOrderInteractor
import com.ably.tracking.demo.publisher.domain.OrderState
import com.ably.tracking.demo.publisher.ui.FakeNavigator
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
internal class MainViewModelTest : BaseViewModelTest() {

    private val destinationLatitude = "51.1065859"
    private val destinationLongitude = "17.0312766"

    private val navigator = FakeNavigator()

    private val orderInteractor = FakeOrderInteractor()

    private val viewModel = MainViewModel(orderInteractor, navigator, baseTestCoroutineDispatcher)

    @Test
    fun `after calling add on view model new order is created`() = runTest {
        // given
        val orderName = "Hawaii pizza"
        viewModel.onLocationPermissionGranted()

        // when
        viewModel.addOrder(orderName, destinationLatitude, destinationLongitude)

        // then
        val order = viewModel.state.value.orders.firstOrNull { it.name == orderName }
        assertThat(order).isNotNull()
        assertThat(order!!.state).isEqualTo(R.string.trackable_state_offline)
    }

    @Test
    fun `after updating order state in asset manager view model state is updated`() =
        runTest {
            // given
            val orderName = "Sushi"
            viewModel.onLocationPermissionGranted()
            viewModel.addOrder(orderName, destinationLatitude, destinationLongitude)

            // when
            orderInteractor.updateOrderState(orderName, OrderState.Publishing)

            // then
            val order = viewModel.state.value.orders.first { it.name == orderName }
            assertThat(order.state).isEqualTo(R.string.trackable_state_publishing)
        }

    @Test
    fun `onTrack clicked selected trackable is tracked`() =
        runTest {
            // given
            val orderName = "Pancake"
            viewModel.onLocationPermissionGranted()
            viewModel.addOrder(orderName, destinationLatitude, destinationLongitude)

            // when
            viewModel.state.value.orders.first().onTrackClicked()

            // then
            assertThat(orderInteractor.trackedOrderId).isEqualTo(orderName)
        }

    @Test
    fun `on remove clicked selected trackable is removed`() =
        runTest {
            // given
            val orderName = "Pancake"
            viewModel.onLocationPermissionGranted()
            viewModel.addOrder(orderName, destinationLatitude, destinationLongitude)

            // when
            viewModel.state.value.orders.first().onRemoveClicked()

            // then
            assertThat(viewModel.state.value.orders).isEmpty()
        }
}
