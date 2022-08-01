package com.ably.tracking.demo.publisher

import com.ably.tracking.demo.publisher.ably.AssetTracker
import com.ably.tracking.demo.publisher.ably.DefaultAssetTracker
import com.ably.tracking.demo.publisher.ably.log.DateFormatterFactory
import com.ably.tracking.demo.publisher.ably.log.DefaultFileManager
import com.ably.tracking.demo.publisher.ably.log.DefaultLogFileWriter
import com.ably.tracking.demo.publisher.ably.log.FileManager
import com.ably.tracking.demo.publisher.ably.log.LocationLogger
import com.ably.tracking.demo.publisher.ably.log.LogFileWriter
import com.ably.tracking.demo.publisher.api.ApiDeliveryServiceDataSource
import com.ably.tracking.demo.publisher.api.DeliveryServiceDataSource
import com.ably.tracking.demo.publisher.api.buildDeliveryServiceApi
import com.ably.tracking.demo.publisher.api.buildOkHttpClient
import com.ably.tracking.demo.publisher.api.buildRetrofit
import com.ably.tracking.demo.publisher.common.NotificationProvider
import com.ably.tracking.demo.publisher.secrets.InMemorySecretsManager
import com.ably.tracking.demo.publisher.secrets.SecretsManager
import com.ably.tracking.demo.publisher.ui.ActivityNavigator
import com.ably.tracking.demo.publisher.ui.Navigator
import com.ably.tracking.demo.publisher.ui.main.MainViewModel
import com.ably.tracking.demo.publisher.ui.settings.SettingsActionsProvider
import com.ably.tracking.demo.publisher.ui.splash.SplashViewModel
import com.google.gson.GsonBuilder
import java.util.Locale
import java.util.TimeZone
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.binds
import org.koin.dsl.module

val appModule = module {

    single {
        DefaultAssetTracker(
            context = get(),
            coroutineDispatcher = Dispatchers.IO,
            notificationProvider = get(),
            locationLogger = get(),
            secretsManager = get()
        )
    } bind AssetTracker::class

    single { ActivityNavigator() } binds arrayOf(ActivityNavigator::class, Navigator::class)

    factory { params -> SettingsActionsProvider(get(), get(), params.get()) }

    factory { GsonBuilder().setPrettyPrinting().create() }

    factory { Locale.getDefault() }

    factory { TimeZone.getDefault() }

    factory { DateFormatterFactory(get(), get()) }

    factory { LocationLogger(get(), get(), get(), get()) }

    factory { DefaultLogFileWriter(get()) } bind LogFileWriter::class

    factory { DefaultFileManager(get()) } bind FileManager::class

    single { NotificationProvider(get()) }

    single { buildOkHttpClient() }

    single { buildRetrofit(get(), BuildConfig.FIREBASE_REGION, BuildConfig.FIREBASE_PROJECT_NAME) }

    single { buildDeliveryServiceApi(get()) }

    single { ApiDeliveryServiceDataSource(get()) } bind DeliveryServiceDataSource::class

    single {
        InMemorySecretsManager(
            get(),
            BuildConfig.AUTHORIZATION_HEADER_BASE_64,
            BuildConfig.AUTHORIZATION_USERNAME
        )
    } bind SecretsManager::class

    viewModel { MainViewModel(get(), get(), Dispatchers.Main) }

    viewModel { SplashViewModel(get(), get(), Dispatchers.Main) }
}
