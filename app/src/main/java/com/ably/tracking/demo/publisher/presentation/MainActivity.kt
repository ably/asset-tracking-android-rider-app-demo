package com.ably.tracking.demo.publisher.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ably.tracking.demo.publisher.presentation.navigation.AndroidJetpackNavigator
import com.ably.tracking.demo.publisher.presentation.navigation.Routes
import com.ably.tracking.demo.publisher.presentation.screens.login.LoginScreen
import com.ably.tracking.demo.publisher.presentation.screens.main.MainScreen
import com.ably.tracking.demo.publisher.presentation.screens.settings.SettingsActionsProvider
import com.ably.tracking.demo.publisher.presentation.screens.settings.SettingsScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.get
import org.koin.core.parameter.parametersOf

@ExperimentalPermissionsApi
class MainActivity : ComponentActivity() {

    private val navigator: AndroidJetpackNavigator by inject(parameters = { parametersOf(this) })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController: NavHostController = rememberNavController()
            navigator.navController = navController
            NavHost(
                navController = navController,
                startDestination = Routes.Login.path
            ) {
                composable(Routes.Login.path) { LoginScreen() }
                composable(Routes.Main.path) { MainScreen(this@MainActivity, navigator) }
                composable(Routes.Settings.path) { SettingsScreen(getSettingsActionsProvider()) }
            }
        }
    }

    @Composable
    private fun getSettingsActionsProvider(): SettingsActionsProvider =
        get(parameters = { parametersOf(navigator) })
}
