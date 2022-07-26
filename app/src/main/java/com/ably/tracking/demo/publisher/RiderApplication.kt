package com.ably.tracking.demo.publisher

import android.app.Application
import com.ably.tracking.demo.publisher.ui.ActivityNavigator
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class RiderApplication : Application() {

    private val navigator: ActivityNavigator by inject()

    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin {
            androidContext(this@RiderApplication)
            modules(appModule)
        }

        this.registerActivityLifecycleCallbacks(navigator)
    }
}
