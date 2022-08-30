package com.ably.tracking.demo.publisher.domain.order

import com.ably.tracking.TrackableState

fun TrackableState.toOrderState(): OrderState = when (this) {
    is TrackableState.Online -> OrderState.Online
    is TrackableState.Publishing -> OrderState.Publishing
    is TrackableState.Failed -> OrderState.Failed
    is TrackableState.Offline -> OrderState.Offline
}
