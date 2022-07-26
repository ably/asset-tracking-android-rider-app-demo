package com.ably.tracking.demo.publisher.ui

import com.ably.tracking.demo.publisher.ui.main.MainActivity
import com.ably.tracking.demo.publisher.ui.settings.SettingsActivity

class FakeNavigator : Navigator {

    val navigationPath = mutableListOf<Class<*>>()

    override fun openMain() {
        navigationPath.add(MainActivity::class.java)
    }

    override fun openSettings() {
        navigationPath.add(SettingsActivity::class.java)
    }
}
