package com.ably.tracking.demo.publisher.ably

import com.ably.tracking.Location
import com.ably.tracking.LocationUpdate
import com.ably.tracking.demo.publisher.ably.log.LocationLogger
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
internal class LocationLoggerTest {

    companion object {
        const val WROCLAW_LATITUDE = 51.1065859
        const val WROCLAW_LONGITUDE = 17.0312766
    }

    private val fileWriter = FakeLogFileWriter()

    private val locationLogger = LocationLogger(fileWriter)

    @Test
    fun `on first call to logLocationUpdate file is created`() = runTest {
        //given
        val locationUpdate = prepareLocationUpdate(time = 1657782376167L)

        //when
        locationLogger.logLocationUpdate(locationUpdate)

        //then
        assertThat(fileWriter.fileName)
            .isEqualTo("14.07_09:06:16_location.log")
    }


    @Test
    fun `on call to logLocationUpdate location log is formatted and appended to the file`() =
        runTest {
            //given
            val firstLocationUpdate = prepareLocationUpdate(time = 1657782376167L)
            val secondLocationUpdate = prepareLocationUpdate(time = 1657782377167L)

            //when
            locationLogger.logLocationUpdate(firstLocationUpdate)
            locationLogger.logLocationUpdate(secondLocationUpdate)

            //then
            assertThat(fileWriter.fileLines[0])
                .isEqualTo("09:06:16 ${firstLocationUpdate.location}")
            assertThat(fileWriter.fileLines[1])
                .isEqualTo("09:06:17 ${secondLocationUpdate.location}")
        }

    private fun prepareLocationUpdate(time: Long) = LocationUpdate(
        Location(
            latitude = WROCLAW_LATITUDE,
            longitude = WROCLAW_LONGITUDE,
            altitude = 0.0,
            accuracy = 0.0f,
            speed = 0.0f,
            bearing = 0.0f,
            time = time
        ), emptyList()
    )
}
