package com.ably.tracking.demo.publisher.ably

import com.ably.tracking.TrackableState
import com.ably.tracking.publisher.Trackable
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface AssetTracker {

    val isConnected: Boolean

    fun connect(clientId: String): SharedFlow<Set<Trackable>>

    suspend fun addTrackable(
        trackableId: String,
        destinationLatitude: Double,
        destinationLongitude: Double
    ): StateFlow<TrackableState>

    suspend fun track(trackableId: String)

    fun getTrackableState(trackableId: String): StateFlow<TrackableState>?

    suspend fun remove(trackableId: String)

    suspend fun disconnect()
}
