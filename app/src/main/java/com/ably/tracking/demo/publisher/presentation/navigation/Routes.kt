package com.ably.tracking.demo.publisher.presentation.navigation

sealed interface Routes {

    object Login : Routes {
        const val path = "login/"
    }

    object Main : Routes {
        const val path = "main/"
    }

    object Settings : Routes {
        const val path = "settings/"
    }
}
