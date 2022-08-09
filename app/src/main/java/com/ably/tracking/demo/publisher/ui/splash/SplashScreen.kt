package com.ably.tracking.demo.publisher.ui.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ably.tracking.demo.publisher.R
import com.ably.tracking.demo.publisher.ui.widget.TextAlertDialog

@Composable
fun SplashScreen(viewModel: SplashViewModel) {
    val state = viewModel.state.collectAsState()
    Scaffold(
        content = {
            SplashScreenContent(
                state.value,
                viewModel::onFetchingSecretsFailedDialogClosed
            )
        },
        modifier = Modifier.fillMaxSize(),
    )
}

@Preview
@Composable
fun SplashScreenContent(
    state: SplashScreenState = SplashScreenState(true),
    onDialogClose: () -> Unit = {}
) {
    if (state.showFetchingSecretsFailedDialog) {
        TextAlertDialog(
            title = R.string.fetching_secrets_failed_dialog_title,
            text = R.string.fetching_secrets_failed_dialog_text,
            onDismiss = onDialogClose
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(64.dp),
            strokeWidth = 6.dp
        )
    }
}
