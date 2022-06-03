package com.ably.tracking.demo.publisher

import com.ably.tracking.TrackableState
import com.ably.tracking.demo.publisher.ably.AssetTracker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeAssetTracker : AssetTracker {

    val trackableStates = mutableMapOf<String, MutableStateFlow<TrackableState>>()

    override fun connect(clientId: String) {
        //no-op
    }

    override suspend fun addTrackable(trackableId: String): StateFlow<TrackableState> {
        val stateFlow = MutableStateFlow<TrackableState>(TrackableState.Offline(null))
        trackableStates[trackableId] = stateFlow
        return stateFlow
    }

    override suspend fun disconnect() {
        //no-op
    }
}
