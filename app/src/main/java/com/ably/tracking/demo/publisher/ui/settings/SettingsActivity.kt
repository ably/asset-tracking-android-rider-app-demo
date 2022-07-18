package com.ably.tracking.demo.publisher.ui.settings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class SettingsActivity : ComponentActivity() {

    private val settingsActionsProvider: SettingsActionsProvider by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { SettingsScreen(::finish, settingsActionsProvider) }
    }

}
