package com.ably.tracking.demo.publisher.common

import androidx.annotation.StringRes
import com.ably.tracking.TrackableState
import com.ably.tracking.demo.publisher.R

@StringRes
fun TrackableState.toStringRes(): Int = when (this) {
    is TrackableState.Online -> R.string.trackable_state_online
    is TrackableState.Publishing -> R.string.trackable_state_publishing
    is TrackableState.Failed -> R.string.trackable_state_failed
    is TrackableState.Offline -> R.string.trackable_state_offline
}
