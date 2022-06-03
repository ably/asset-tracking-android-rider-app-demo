package com.ably.tracking.demo.publisher.ably

import android.annotation.SuppressLint
import android.content.Context
import com.ably.tracking.Accuracy
import com.ably.tracking.Resolution
import com.ably.tracking.TrackableState
import com.ably.tracking.connection.Authentication
import com.ably.tracking.connection.ConnectionConfiguration
import com.ably.tracking.demo.publisher.BuildConfig
import com.ably.tracking.publisher.*
import kotlinx.coroutines.flow.StateFlow

class AssetTrackerImpl(
    private val context: Context,
    private val mapBoxAccessToken: String,
    private val ablyApiKey: String
) : AssetTracker {
    private var publisher: Publisher? = null

    @SuppressLint("MissingPermission")
    override fun connect(clientId: String) {
        // Prepare the default resolution for the Resolution Policy
        val defaultResolution =
            Resolution(Accuracy.BALANCED, desiredInterval = 1000L, minimumDisplacement = 1.0)

        publisher = Publisher.publishers() // get the Publisher builder in default state
            .connection(
                ConnectionConfiguration(
                    Authentication.basic(
                        clientId,
                        ablyApiKey
                    )
                )
            ) // provide Ably configuration with credentials
            .map(MapConfiguration(mapBoxAccessToken)) // provide Mapbox configuration with credentials
            .androidContext(context) // provide Android runtime context
            .resolutionPolicy(
                DefaultResolutionPolicyFactory(
                    defaultResolution,
                    context
                )
            ) // provide either the default resolution policy factory or your custom implementation
            .profile(RoutingProfile.CYCLING) // provide mode of transportation for better location enhancements
            .logHandler(TrackerLogHandler())
            .backgroundTrackingNotificationProvider(
                TrackerPublisherNotificationProvider(context),
                97852
            )
            .start()
    }

    override suspend fun addTrackable(trackableId: String): StateFlow<TrackableState> =
        publisher!!.let {
            val trackable = Trackable(id = trackableId)
            it.add(trackable)
        }

    override suspend fun track(trackableId: String) {
        val trackable = publisher!!.trackables.replayCache.last().first { it.id == trackableId }
        publisher!!.track(trackable)
    }

    override suspend fun disconnect() {
        publisher?.stop()
        publisher = null
    }
}
