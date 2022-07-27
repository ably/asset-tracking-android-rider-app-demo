package com.ably.tracking.demo.publisher.ui

import android.app.Activity
import android.content.Intent
import com.ably.tracking.demo.publisher.common.ActivityLifecycleCallbacksAdapter
import com.ably.tracking.demo.publisher.ui.main.MainActivity
import com.ably.tracking.demo.publisher.ui.settings.SettingsActivity

class ActivityNavigator : Navigator, ActivityLifecycleCallbacksAdapter() {

    private var currentActivity: Activity? = null

    override fun openMain() {
        navigateToActivity(MainActivity::class.java)
    }

    override fun openSettings() {
        navigateToActivity(SettingsActivity::class.java)
    }

    private fun navigateToActivity(activityClass: Class<*>) {
        currentActivity?.let {
            val intent = Intent(it, activityClass)
            it.startActivity(intent)
        }
    }

    override fun closeCurrentScreen() {
        currentActivity?.finish()
    }

    override fun onActivityResumed(activity: Activity) {
        super.onActivityResumed(activity)
        currentActivity = activity
    }

    override fun onActivityPaused(activity: Activity) {
        super.onActivityPaused(activity)
        if (currentActivity == activity) {
            currentActivity = null
        }
    }
}
