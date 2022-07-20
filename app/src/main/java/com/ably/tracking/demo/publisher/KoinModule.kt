package com.ably.tracking.demo.publisher

import com.ably.tracking.demo.publisher.ably.AssetTracker
import com.ably.tracking.demo.publisher.ably.DefaultAssetTracker
import com.ably.tracking.demo.publisher.ably.log.DateFormatterFactory
import com.ably.tracking.demo.publisher.ably.log.DefaultFileManager
import com.ably.tracking.demo.publisher.ably.log.DefaultLogFileWriter
import com.ably.tracking.demo.publisher.ably.log.FileManager
import com.ably.tracking.demo.publisher.ably.log.LocationLogger
import com.ably.tracking.demo.publisher.ably.log.LogFileWriter
import com.ably.tracking.demo.publisher.common.NotificationProvider
import com.ably.tracking.demo.publisher.ui.main.MainViewModel
import com.ably.tracking.demo.publisher.ui.settings.SettingsActionsProvider
import com.google.gson.GsonBuilder
import java.util.Locale
import java.util.TimeZone
import java.util.UUID
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {

    single {
        DefaultAssetTracker(
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

    factory { GsonBuilder().setPrettyPrinting().create() }

    factory { Locale.getDefault() }

    factory { TimeZone.getDefault() }

    factory { DateFormatterFactory(get(), get()) }

    factory { LocationLogger(get(), get(), get(), get()) }

    factory { DefaultLogFileWriter(get()) } bind LogFileWriter::class

    factory { DefaultFileManager(get()) } bind FileManager::class

    single { NotificationProvider(get()) }

    viewModel { MainViewModel(get(), get(), Dispatchers.Main) }
}
