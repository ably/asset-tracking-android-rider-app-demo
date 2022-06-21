package com.ably.tracking.demo.publisher

import com.ably.tracking.TrackableState
import com.ably.tracking.demo.publisher.ably.AssetTracker
import com.ably.tracking.publisher.Trackable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

class FakeAssetTracker : AssetTracker {

    var connectCallsCount = 0

    override var isConnected: Boolean = false

    val trackableStates = mutableMapOf<String, MutableStateFlow<TrackableState>>()

    var trackedTrackableId: String? = null

    val trackables = MutableStateFlow(emptySet<Trackable>())

    override fun connect(clientId: String): SharedFlow<Set<Trackable>> {
        connectCallsCount++
        isConnected = true
        return trackables
    }

    override suspend fun addTrackable(
        trackableId: String,
        destinationLatitude: Double,
        destinationLongitude: Double
    ): StateFlow<TrackableState> {
        val stateFlow = MutableStateFlow<TrackableState>(TrackableState.Offline(null))
        trackableStates[trackableId] = stateFlow
        trackables.emit(trackables.value.plus(Trackable(trackableId)))
        return stateFlow
    }

    override suspend fun track(trackableId: String) {
        trackedTrackableId = trackableId
    }

    override fun getTrackableState(trackableId: String): StateFlow<TrackableState>? =
        trackableStates[trackableId]

    override suspend fun remove(trackableId: String) {
        trackableStates.remove(trackableId)
        val trackable = trackables.value.first { it.id == trackableId }
        trackables.emit(trackables.value.minus(trackable))
    }

    override suspend fun disconnect() {
        isConnected = false
    }
}
