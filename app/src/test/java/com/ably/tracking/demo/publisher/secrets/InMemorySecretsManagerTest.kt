package com.ably.tracking.demo.publisher.secrets

import com.ably.tracking.demo.publisher.BuildConfig
import com.ably.tracking.demo.publisher.api.FakeDeliveryServiceApiSource
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
internal class InMemorySecretsManagerTest {

    private val deliveryServiceApiSource = FakeDeliveryServiceApiSource()

    private val inMemorySecretsManager = InMemorySecretsManager(deliveryServiceApiSource, BuildConfig.AUTHORIZATION_HEADER_BASE_64)

    @Test
    fun `getMapboxToken return token from api after loadSecrets`() = runTest {
        // given
        deliveryServiceApiSource.mapboxToken = "notARealMapboxToken"

        // when
        inMemorySecretsManager.loadSecrets()
        val mapboxToken = inMemorySecretsManager.getMapboxToken()

        // then
        Truth.assertThat(mapboxToken)
            .isEqualTo("notARealMapboxToken")
    }

    @Test
    fun `getAblyToken return token from api after loadSecrets`() = runTest {
        // given
        deliveryServiceApiSource.ablyToken = "notARealAblyToken"

        // when
        inMemorySecretsManager.loadSecrets()
        val mapboxToken = inMemorySecretsManager.getAblyToken()

        // then
        Truth.assertThat(mapboxToken)
            .isEqualTo("notARealAblyToken")
    }
}
