package com.ably.tracking.demo.publisher

import android.app.Application
import com.ably.tracking.demo.publisher.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class RiderApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin{
            androidContext(this@RiderApplication)
            modules(appModule)
        }
    }
}
