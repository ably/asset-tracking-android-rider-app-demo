package com.ably.tracking.demo.publisher.ably

import android.annotation.SuppressLint
import android.content.Context
import com.ably.tracking.Accuracy
import com.ably.tracking.Resolution
import com.ably.tracking.TrackableState
import com.ably.tracking.connection.Authentication
import com.ably.tracking.connection.ConnectionConfiguration
import com.ably.tracking.demo.publisher.common.NotificationProvider
import com.ably.tracking.publisher.DefaultProximity
import com.ably.tracking.publisher.DefaultResolutionConstraints
import com.ably.tracking.publisher.DefaultResolutionPolicyFactory
import com.ably.tracking.publisher.DefaultResolutionSet
import com.ably.tracking.publisher.Destination
import com.ably.tracking.publisher.MapConfiguration
import com.ably.tracking.publisher.Publisher
import com.ably.tracking.publisher.RoutingProfile
import com.ably.tracking.publisher.Trackable
import kotlinx.coroutines.flow.StateFlow

class AssetTrackerImpl(
    private val context: Context,
    private val notificationProvider: NotificationProvider,
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
                PublisherNotificationProviderAdapter(notificationProvider),
                notificationProvider.notificationId
            )
            .start()
    }

    override suspend fun addTrackable(
        trackableId: String,
        destinationLatitude: Double,
        destinationLongitude: Double
    ): StateFlow<TrackableState> =
        publisher!!.let {
            val trackable =
                Trackable(
                    id = trackableId,
                    Destination(destinationLatitude, destinationLongitude),
                    constraints = getOrderResolutionConstraints()
                )
            it.add(trackable)
        }

    private fun getOrderResolutionConstraints() =
        DefaultResolutionConstraints(
            resolutions = getResolutionSet(),
            proximityThreshold = DefaultProximity(spatial = 1.0),
            batteryLevelThreshold = 15.0f,
            lowBatteryMultiplier = 5.0f
        )

    private fun getResolutionSet() =
        DefaultResolutionSet(
            farWithoutSubscriber = Resolution(
                accuracy = Accuracy.MINIMUM,
                desiredInterval = 2000L,
                minimumDisplacement = 100.0
            ),
            farWithSubscriber = Resolution(
                accuracy = Accuracy.BALANCED,
                desiredInterval = 1000L,
                minimumDisplacement = 10.0
            ),
            nearWithoutSubscriber = Resolution(
                accuracy = Accuracy.BALANCED,
                desiredInterval = 1000L,
                minimumDisplacement = 10.0
            ),
            nearWithSubscriber = Resolution(
                accuracy = Accuracy.HIGH,
                desiredInterval = 500L,
                minimumDisplacement = 1.0
            )
        )


    override suspend fun track(trackableId: String) {
        val trackable = publisher!!.trackables.replayCache.last().first { it.id == trackableId }
        publisher!!.track(trackable)
    }

    override suspend fun disconnect() {
        publisher?.stop()
        publisher = null
    }
}
