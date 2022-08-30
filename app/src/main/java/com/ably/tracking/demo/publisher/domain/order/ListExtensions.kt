package com.ably.tracking.demo.publisher.domain.order

fun <T> List<T>.copyAndReplaceElementAt(index: Int, element: T) =
    subList(0, index) + element + subList(index + 1, size)
