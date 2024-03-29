package com.ably.tracking.demo.publisher.data.api

import com.ably.tracking.demo.publisher.BuildConfig
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Ignore
import org.junit.Test

@ExperimentalCoroutinesApi
@Ignore("Ignoring as those tests perform actual api calls")
internal class ApiDeliveryServiceDataSourceTest {

    private val okHttpClient = buildOkHttpClient()

    private val retrofit =
        buildRetrofit(okHttpClient, BuildConfig.FIREBASE_REGION, BuildConfig.FIREBASE_PROJECT_NAME)

    private val deliveryServiceApi = buildDeliveryServiceApi(retrofit)

    private val deliveryServiceApiSource = ApiDeliveryServiceDataSource(deliveryServiceApi)

    private val authorizationHeader = BuildConfig.AUTHORIZATION_HEADER_BASE_64

    @Test
    fun `call to getMapboxApiKey returns non-empty value`() = runTest {
        // given

        // when
        val mapboxToken = deliveryServiceApiSource.getMapboxToken(authorizationHeader)

        // then
        assertThat(mapboxToken)
            .isNotEmpty()
    }

    @Test
    fun `call to getAblyToken returns non-empty value`() = runTest {
        // given

        // when
        val ablyToken = deliveryServiceApiSource.getAblyToken(authorizationHeader)

        // then
        assertThat(ablyToken)
            .isNotEmpty()
    }

    @Test
    fun `call to assignOrder returns non-empty value`() = runTest {
        // given

        // when
        val result = deliveryServiceApiSource.assignOrder(authorizationHeader, 5)

        // then
        assertThat(result)
            .isNotNull()
    }

    @Test
    fun `call to deleteOrder returns non-empty value`() = runTest {
        // given

        // when
        val result = deliveryServiceApiSource.deleteOrder(authorizationHeader, 5)

        // then
        assertThat(result)
            .isNotNull()
    }
}
