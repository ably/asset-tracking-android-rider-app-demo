package com.ably.tracking.demo.publisher.di

import com.ably.tracking.demo.publisher.BuildConfig
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
import com.ably.tracking.demo.publisher.domain.DefaultOrderInteractor
import com.ably.tracking.demo.publisher.domain.OrderManager
import com.ably.tracking.demo.publisher.secrets.AndroidBase64Encoder
import com.ably.tracking.demo.publisher.secrets.Base64Encoder
import com.ably.tracking.demo.publisher.secrets.InMemorySecretsManager
import com.ably.tracking.demo.publisher.secrets.SecretsManager
import com.ably.tracking.demo.publisher.secrets.SecretsStorage
import com.ably.tracking.demo.publisher.secrets.SharedPreferencesSecretsStorage
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
        DefaultOrderInteractor(
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
