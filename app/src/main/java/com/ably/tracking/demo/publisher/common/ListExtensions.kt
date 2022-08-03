package com.ably.tracking.demo.publisher.common

fun <T> List<T>.copyAndReplaceElementAt(index: Int, element: T) =
    subList(0, index) + element + subList(index, size - 1)
