package com.ably.tracking.demo.publisher.secrets

import com.ably.tracking.demo.publisher.BuildConfig
import com.ably.tracking.demo.publisher.api.FakeDeliveryServiceDataSource
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
internal class InMemorySecretsManagerTest {

    private val deliveryServiceApiSource = FakeDeliveryServiceDataSource()

    private val inMemorySecretsManager =
        InMemorySecretsManager(
            deliveryServiceApiSource,
            BuildConfig.AUTHORIZATION_HEADER_BASE_64,
            BuildConfig.AUTHORIZATION_USERNAME
        )

    @Test
    fun `getMapboxToken return token from api after loadSecrets`() = runTest {
        // given
        val username = "rider"
        val password = "password"
        deliveryServiceApiSource.mapboxToken = "notARealMapboxToken"

        // when
        inMemorySecretsManager.loadSecrets(username, password)
        val mapboxToken = inMemorySecretsManager.getMapboxToken()

        // then
        Truth.assertThat(mapboxToken)
            .isEqualTo("notARealMapboxToken")
    }

    @Test
    fun `getAblyToken return token from api after loadSecrets`() = runTest {
        // given
        val username = "rider"
        val password = "password"
        deliveryServiceApiSource.ablyToken = "notARealAblyToken"

        // when
        inMemorySecretsManager.loadSecrets(username, password)
        val ablyToken = inMemorySecretsManager.getAblyToken()

        // then
        Truth.assertThat(ablyToken)
            .isEqualTo("notARealAblyToken")
    }
}
