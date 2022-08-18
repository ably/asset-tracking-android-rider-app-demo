package com.ably.tracking.demo.publisher.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions

class AndroidJetpackNavigator : Navigator {

    lateinit var navController: NavController

    override fun openMain() {
        val navOptions = NavOptions.Builder().setPopUpTo(Routes.Login.path, true).build()
        navController.navigate(Routes.Main.path, navOptions)
    }

    override fun openSettings() {
        navController.navigate(Routes.Settings.path)
    }

    override fun goBack() {
        navController.popBackStack()
    }
}
