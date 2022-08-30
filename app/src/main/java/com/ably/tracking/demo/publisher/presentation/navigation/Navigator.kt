package com.ably.tracking.demo.publisher.presentation.navigation

import android.net.Uri
import androidx.annotation.StringRes

interface Navigator {
    fun openMain()
    fun openSettings()
    fun goBack()
    fun share(@StringRes logShareHeader: Int, shareFiles: List<Uri>)
    fun navigateToAppSettingsScreen()
}
