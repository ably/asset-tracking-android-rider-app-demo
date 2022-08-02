package com.ably.tracking.demo.publisher.domain

import com.ably.tracking.TrackableState
import com.ably.tracking.demo.publisher.ably.AssetTracker
import com.ably.tracking.demo.publisher.api.DeliveryServiceDataSource
import com.ably.tracking.publisher.Trackable
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

class OrderInteractor(
    private val assetTracker: AssetTracker,
    private val deliveryServiceDataSource: DeliveryServiceDataSource,
    private val authorizationHeaderBase64: String
) {


    fun connect(): SharedFlow<Set<Trackable>> = assetTracker.connect()

    suspend fun addTrackable(
        trackableId: String,
        destinationLatitude: Double,
        destinationLongitude: Double
    ): StateFlow<TrackableState> {
        deliveryServiceDataSource.assignOrder(authorizationHeaderBase64, trackableId.toLong())
        return assetTracker.addTrackable(trackableId, destinationLatitude, destinationLongitude)
    }

    suspend fun track(trackableId: String) = assetTracker.track(trackableId)

    fun getTrackableState(trackableId: String): StateFlow<TrackableState>? =
        assetTracker.getTrackableState(trackableId)

    suspend fun remove(trackableId: String) = assetTracker.remove(trackableId)

    suspend fun disconnect() = assetTracker.disconnect()

}
