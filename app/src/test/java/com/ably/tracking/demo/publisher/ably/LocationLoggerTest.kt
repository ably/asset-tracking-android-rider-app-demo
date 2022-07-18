package com.ably.tracking.demo.publisher.ably

import com.ably.tracking.Location
import com.ably.tracking.LocationUpdate
import com.ably.tracking.demo.publisher.ably.log.LocationLogger
import com.ably.tracking.publisher.GeoJsonGeometry
import com.ably.tracking.publisher.GeoJsonMessage
import com.ably.tracking.publisher.GeoJsonProperties
import com.ably.tracking.publisher.LocationHistoryData
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
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

    private val fileManager = FakeFileManager()

    private val gson = Gson()

    private val locationLogger = LocationLogger(fileWriter, fileManager, gson)

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

    @Test
    fun `call to logLocationHistoryDataAndClose writes JSON-serialized data to a file`() = runTest {
        //given
        val locationUpdate = prepareLocationUpdate(time = 1657782376167L)
        locationLogger.logLocationUpdate(locationUpdate)

        val locationHistoryData = LocationHistoryData(
            listOf(
                GeoJsonMessage(
                    type = "Feature",
                    geometry = GeoJsonGeometry(
                        type = "Point",
                        coordinates = listOf(17.032827, 51.1052855, 0.0)
                    ),
                    properties = GeoJsonProperties(
                        accuracyHorizontal = 699.999f,
                        bearing = 100f,
                        speed = 50.0f,
                        time = 1231244123.0
                    )
                )
            )
        )
        val locationHistoryDataJson =
            "{" +
                "\"events\":[" +
                "{" +
                "\"type\":\"Feature\"," +
                "\"geometry\":{" +
                "\"type\":\"Point\"," +
                "\"coordinates\":[" +
                "17.032827," +
                "51.1052855," +
                "0.0" +
                "]" +
                "}," +
                "\"properties\":{" +
                "\"accuracyHorizontal\":699.999," +
                "\"bearing\":100.0," +
                "\"speed\":50.0," +
                "\"time\":1.231244123E9" +
                "}" +
                "}" +
                "]," +
                "\"version\":1" +
                "}"

        //when
        locationLogger.logLocationHistoryDataAndClose(
            locationHistoryData
        )

        //then
        assertThat(fileWriter.fileLines[1])
            .isEqualTo(locationHistoryDataJson)
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
