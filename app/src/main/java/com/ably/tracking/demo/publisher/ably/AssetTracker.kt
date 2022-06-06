package com.ably.tracking.demo.publisher.ably

import android.content.Context
import com.ably.tracking.TrackableState
import com.ably.tracking.demo.publisher.BuildConfig
import kotlinx.coroutines.flow.StateFlow

interface AssetTracker {

    fun connect(clientId: String)

    suspend fun addTrackable(
        trackableId: String,
        destinationLatitude: Double,
        destinationLongitude: Double
    ): StateFlow<TrackableState>

    suspend fun track(trackableId: String)

    suspend fun disconnect()

    companion object {
        fun build(context: Context) = AssetTrackerImpl(
            context = context,
            mapBoxAccessToken = BuildConfig.MAPBOX_ACCESS_TOKEN,
            ablyApiKey = BuildConfig.ABLY_API_KEY
        )
    }
}
