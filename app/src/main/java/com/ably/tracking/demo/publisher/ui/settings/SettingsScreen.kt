package com.ably.tracking.demo.publisher.ui.settings

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
import com.ably.tracking.demo.publisher.ui.navigation.Navigator
import com.ably.tracking.demo.publisher.ui.theme.AATPublisherDemoTheme
import com.ably.tracking.demo.publisher.ui.widget.AATAppBar
import com.ably.tracking.demo.publisher.ui.widget.StyledTextButton
import com.ably.tracking.demo.publisher.ui.widget.TextButton
import org.koin.androidx.compose.get

@Composable
fun SettingsScreen(
    debugActionsProvider: SettingsActionsProvider,
    navigator: Navigator = get()
) {
    AATPublisherDemoTheme {
        Scaffold(
            topBar = {
                AATAppBar(navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.settings_screen_back_description),
                        modifier = Modifier.clickable(onClick = navigator::goBack)
                    )
                })
            },
            content = { DebugScreenContent(debugActionsProvider) },
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
fun DebugScreenContent(debugActionsProvider: SettingsActionsProvider) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        StyledTextButton(text = R.string.settings_screen_close_session_button_text) { debugActionsProvider.closeSession() }
        StyledTextButton(text = R.string.settings_screen_export_logs_button_text) { debugActionsProvider.exportLogs() }
        StyledTextButton(text = R.string.settings_screen_remove_logs_button_text) { debugActionsProvider.removeLogs() }
        StyledTextButton(text = R.string.settings_screen_start_session_button_text) { debugActionsProvider.startSession() }
        StyledTextButton(text = R.string.settings_screen_restart_app_button_text) {
            debugActionsProvider.restartApplication()
        }
    }
}
