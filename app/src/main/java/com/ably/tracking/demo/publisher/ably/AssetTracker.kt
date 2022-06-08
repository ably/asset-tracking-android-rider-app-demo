package com.ably.tracking.demo.publisher.ably

import com.ably.tracking.TrackableState
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
}
