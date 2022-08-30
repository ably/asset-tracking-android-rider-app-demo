package com.ably.tracking.demo.publisher.common

import com.ably.tracking.demo.publisher.domain.order.copyAndReplaceElementAt
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
internal class ListExtensionsKtTest {

    @Test
    fun `copyAndReplaceElementAt should alter only element at provided index`() = runTest {
        // given
        val input = listOf(1, 2, 3, 4, 5, 6)

        // when
        val output = input.copyAndReplaceElementAt(index = 2, element = 9)

        // then
        assertThat(output).isEqualTo(listOf(1, 2, 9, 4, 5, 6))
    }
}
