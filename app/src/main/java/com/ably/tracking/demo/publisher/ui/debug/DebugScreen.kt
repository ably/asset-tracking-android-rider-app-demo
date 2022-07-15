package com.ably.tracking.demo.publisher.ui.debug

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.ably.tracking.demo.publisher.R
import com.ably.tracking.demo.publisher.ui.widget.TextButton


@Composable
fun DebugScreen(onClose: () -> Unit = {}, debugActionsProvider: DebugActionsProvider) {
    Scaffold(
        topBar = {
            TopAppBar(
                // Provide Title
                title = {
                    Text(text = stringResource(R.string.debug_screen_title), color = Color.White)
                },
                // Provide the navigation Icon (Icon on the left to toggle drawer)
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.debug_screen_back_description),
                        modifier = Modifier.clickable(onClick = onClose)
                    )
                }
            )
        },
        content = { DebugScreenContent(debugActionsProvider) },
        modifier = Modifier.fillMaxSize(),
    )
}

@Composable
fun DebugScreenContent(debugActionsProvider: DebugActionsProvider) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TextButton(text = R.string.debug_screen_close_session_button_text) { debugActionsProvider.closeSession() }
        TextButton(text = R.string.debug_screen_export_logs_button_text) { debugActionsProvider.exportLogs() }
        TextButton(text = R.string.debug_screen_remove_logs_button_text) { debugActionsProvider.removeLogs() }
        TextButton(text = R.string.debug_screen_start_session_button_text) { debugActionsProvider.startSession() }
        TextButton(text = R.string.debug_screen_restart_app_button_text) { debugActionsProvider.restartApplication() }
    }
}
