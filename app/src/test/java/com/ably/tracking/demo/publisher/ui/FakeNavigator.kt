package com.ably.tracking.demo.publisher.ui

import com.ably.tracking.demo.publisher.ui.navigation.Navigator
import com.ably.tracking.demo.publisher.ui.navigation.Routes

class FakeNavigator : Navigator {

    val navigationPath = mutableListOf<String>()

    override fun openMain() {
        navigationPath.add(Routes.Main.path)
    }

    override fun openSettings() {
        navigationPath.add(Routes.Settings.path)
    }

    override fun goBack() {
        // no-op
    }
}
