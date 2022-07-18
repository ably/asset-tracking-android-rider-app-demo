package com.ably.tracking.demo.publisher

import com.ably.tracking.demo.publisher.ably.AssetTracker
import com.ably.tracking.demo.publisher.ably.AssetTrackerImpl
import com.ably.tracking.demo.publisher.ably.log.FileManager
import com.ably.tracking.demo.publisher.ably.log.FileManagerImpl
import com.ably.tracking.demo.publisher.ably.log.LogFileWriterImpl
import com.ably.tracking.demo.publisher.ably.log.LogFileWriter
import com.ably.tracking.demo.publisher.ably.log.LocationLogger
import com.ably.tracking.demo.publisher.common.NotificationProvider
import com.ably.tracking.demo.publisher.ui.settings.SettingsActionsProvider
import com.ably.tracking.demo.publisher.ui.main.MainViewModel
import com.google.gson.Gson
import java.util.UUID
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

    single { UUID.randomUUID().toString() }

    factory { params -> SettingsActionsProvider(get(), get(), params.get(), get()) }

    factory { Gson() }

    factory { LocationLogger(get(), get(), get()) }

    factory { LogFileWriterImpl(get()) } bind LogFileWriter::class

    factory { FileManagerImpl(get()) } bind FileManager::class

    single { NotificationProvider(get()) }

    viewModel { MainViewModel(get(), get(), Dispatchers.Main) }
}
