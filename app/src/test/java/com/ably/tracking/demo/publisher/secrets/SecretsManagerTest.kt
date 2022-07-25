package com.ably.tracking.demo.publisher.secrets

import com.ably.tracking.demo.publisher.BuildConfig
import com.ably.tracking.demo.publisher.api.FakeDeliveryServiceApiSource
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
internal class SecretsManagerTest {

    private val deliveryServiceApiSource = FakeDeliveryServiceApiSource()

    private val secretsManager = SecretsManager(deliveryServiceApiSource, BuildConfig.AUTHORIZATION_HEADER_BASE_64)

    @Test
    fun `getMapboxToken return token from api after loadSecrets`() = runTest {
        // given
        deliveryServiceApiSource.mapboxToken = "notARealMapboxToken"

        // when
        secretsManager.loadSecrets()
        val mapboxToken = secretsManager.getMapboxToken()

        // then
        Truth.assertThat(mapboxToken)
            .isEqualTo("notARealMapboxToken")
    }

    @Test
    fun `getAblyToken return token from api after loadSecrets`() = runTest {
        // given
        deliveryServiceApiSource.ablyToken = "notARealAblyToken"

        // when
        secretsManager.loadSecrets()
        val mapboxToken = secretsManager.getAblyToken()

        // then
        Truth.assertThat(mapboxToken)
            .isEqualTo("notARealAblyToken")
    }
}
