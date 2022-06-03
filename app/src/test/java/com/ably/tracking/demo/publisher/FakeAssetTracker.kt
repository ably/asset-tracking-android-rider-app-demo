package com.ably.tracking.demo.publisher

import com.ably.tracking.TrackableState
import com.ably.tracking.demo.publisher.ably.AssetTracker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeAssetTracker : AssetTracker {

    val trackableStates = mutableMapOf<String, StateFlow<TrackableState>>()

    override fun connect(clientId: String) {
        //no-op
    }

    override suspend fun addTrackable(trackableId: String): StateFlow<TrackableState> =
        MutableStateFlow(TrackableState.Offline(null)).apply {
            trackableStates[trackableId] = this
        }

    override suspend fun disconnect() {
        //no-op
    }
}
