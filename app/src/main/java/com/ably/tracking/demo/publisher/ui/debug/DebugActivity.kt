package com.ably.tracking.demo.publisher.ui.debug

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class DebugActivity : ComponentActivity() {

    private val debugActionsProvider: DebugActionsProvider by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { DebugScreen(::finish, debugActionsProvider) }
    }

}
