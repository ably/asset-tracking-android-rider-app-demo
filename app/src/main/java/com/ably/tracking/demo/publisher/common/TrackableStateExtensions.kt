package com.ably.tracking.demo.publisher.common

import com.ably.tracking.TrackableState
import com.ably.tracking.demo.publisher.domain.order.OrderState

fun TrackableState.toOrderState(): OrderState = when (this) {
    is TrackableState.Online -> OrderState.Online
    is TrackableState.Publishing -> OrderState.Publishing
    is TrackableState.Failed -> OrderState.Failed
    is TrackableState.Offline -> OrderState.Offline
}
