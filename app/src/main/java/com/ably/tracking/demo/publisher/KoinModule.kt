package com.ably.tracking.demo.publisher

import com.ably.tracking.demo.publisher.ably.AssetTracker
import com.ably.tracking.demo.publisher.ably.AssetTrackerImpl
import com.ably.tracking.demo.publisher.ably.LogFileWriterImpl
import com.ably.tracking.demo.publisher.ably.LogFileWriter
import com.ably.tracking.demo.publisher.ably.LocationLogger
import com.ably.tracking.demo.publisher.common.NotificationProvider
import com.ably.tracking.demo.publisher.main.MainViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {

    single {
        AssetTrackerImpl(
            context = get(),
            coroutineDispatcher = Dispatchers.IO,
            notificationProvider = get(),
            locationLogger = get(),
            mapBoxAccessToken = BuildConfig.MAPBOX_ACCESS_TOKEN,
            ablyApiKey = BuildConfig.ABLY_API_KEY
        )
    } bind AssetTracker::class

    factory { LocationLogger(get()) }

    factory { LogFileWriterImpl(get()) } bind LogFileWriter::class

    single { NotificationProvider(get()) }

    viewModel { MainViewModel(get(), Dispatchers.Main) }
}
