package com.ably.tracking.demo.publisher.ui.navigation

import android.net.Uri
import androidx.annotation.StringRes

interface Navigator {
    fun openMain()
    fun openSettings()
    fun goBack()
    fun share(@StringRes logShareHeader: Int, shareFiles: List<Uri>)
}
