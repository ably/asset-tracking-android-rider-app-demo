package com.ably.tracking.demo.publisher.di

import com.ably.tracking.demo.publisher.BuildConfig
import com.ably.tracking.demo.publisher.data.ably.AssetTracker
import com.ably.tracking.demo.publisher.data.ably.DefaultAssetTracker
import com.ably.tracking.demo.publisher.data.ably.log.DateFormatterFactory
import com.ably.tracking.demo.publisher.data.ably.log.DefaultFileManager
import com.ably.tracking.demo.publisher.data.ably.log.DefaultLogFileWriter
import com.ably.tracking.demo.publisher.data.ably.log.FileManager
import com.ably.tracking.demo.publisher.data.ably.log.LocationLogger
import com.ably.tracking.demo.publisher.data.ably.log.LogFileWriter
import com.ably.tracking.demo.publisher.data.api.ApiDeliveryServiceDataSource
import com.ably.tracking.demo.publisher.domain.DeliveryServiceDataSource
import com.ably.tracking.demo.publisher.data.api.buildDeliveryServiceApi
import com.ably.tracking.demo.publisher.data.api.buildOkHttpClient
import com.ably.tracking.demo.publisher.data.api.buildRetrofit
import com.ably.tracking.demo.publisher.common.NotificationProvider
import com.ably.tracking.demo.publisher.domain.order.DefaultOrderManager
import com.ably.tracking.demo.publisher.domain.order.OrderManager
import com.ably.tracking.demo.publisher.data.secrets.AndroidBase64Encoder
import com.ably.tracking.demo.publisher.domain.secrets.Base64Encoder
import com.ably.tracking.demo.publisher.domain.secrets.InMemorySecretsManager
import com.ably.tracking.demo.publisher.domain.secrets.SecretsManager
import com.ably.tracking.demo.publisher.domain.secrets.SecretsStorage
import com.ably.tracking.demo.publisher.data.secrets.SharedPreferencesSecretsStorage
import com.ably.tracking.demo.publisher.presentation.screens.login.LoginViewModel
import com.ably.tracking.demo.publisher.presentation.screens.main.MainViewModel
import com.ably.tracking.demo.publisher.presentation.navigation.AndroidJetpackNavigator
import com.ably.tracking.demo.publisher.presentation.navigation.Navigator
import com.ably.tracking.demo.publisher.presentation.screens.settings.SettingsActionsProvider
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

    single { params -> AndroidJetpackNavigator(params.get()) } binds arrayOf(
        AndroidJetpackNavigator::class,
        Navigator::class
    )

    factory { params -> SettingsActionsProvider(get(), get(), params.get()) }

    factory { GsonBuilder().setPrettyPrinting().create() }

    factory { Locale.getDefault() }

    factory { TimeZone.getDefault() }

    factory { DateFormatterFactory(get(), get()) }

    factory { LocationLogger(get(), get(), get(), get()) }

    factory { DefaultLogFileWriter(get()) } bind LogFileWriter::class

    factory { DefaultFileManager(get()) } bind FileManager::class

    single { NotificationProvider(get()) }

    single {
        DefaultOrderManager(
            get(),
            get(),
            get(),
            Dispatchers.Default
        )
    } bind OrderManager::class

    single { buildOkHttpClient() }

    single { buildRetrofit(get(), BuildConfig.FIREBASE_REGION, BuildConfig.FIREBASE_PROJECT_NAME) }

    single { buildDeliveryServiceApi(get()) }

    single { ApiDeliveryServiceDataSource(get()) } bind DeliveryServiceDataSource::class

    single {
        InMemorySecretsManager(
            get(),
            get(),
            get()
        )
    } bind SecretsManager::class

    single {
        SharedPreferencesSecretsStorage(
            get()
        )
    } bind SecretsStorage::class

    factory { AndroidBase64Encoder() } bind Base64Encoder::class

    viewModel { MainViewModel(get(), get(), Dispatchers.Main) }

    viewModel { LoginViewModel(get(), get(), Dispatchers.Main) }
}
