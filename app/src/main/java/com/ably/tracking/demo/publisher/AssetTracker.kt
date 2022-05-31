package com.ably.tracking.demo.publisher

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import com.ably.tracking.Accuracy
import com.ably.tracking.Resolution
import com.ably.tracking.connection.Authentication
import com.ably.tracking.connection.ConnectionConfiguration
import com.ably.tracking.publisher.*

class AssetTracker(
    private val mapBoxAccessToken: String,
    private val ablyClientId: String,
    private val ablyApiKey: String
) {
    private var publisher: Publisher? = null

    @SuppressLint("MissingPermission")
    fun connect(context: Context) {
        // Prepare the default resolution for the Resolution Policy
        val defaultResolution =
            Resolution(Accuracy.BALANCED, desiredInterval = 1000L, minimumDisplacement = 1.0)

        publisher = Publisher.publishers() // get the Publisher builder in default state
            .connection(
                ConnectionConfiguration(
                    Authentication.basic(
                        ablyClientId,
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
            .start()
    }

    suspend fun disconnect() {
        publisher?.stop()
        publisher = null
    }

    companion object {
        fun build() = AssetTracker(
            mapBoxAccessToken = BuildConfig.MAPBOX_ACCESS_TOKEN,
            ablyClientId = BuildConfig.ABLY_APP_ID,
            ablyApiKey = BuildConfig.ABLY_API_KEY
        )
    }
}
