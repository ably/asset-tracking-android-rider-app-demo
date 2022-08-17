package com.ably.tracking.demo.publisher.ui.login

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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.getViewModel
import com.ably.tracking.demo.publisher.R
import com.ably.tracking.demo.publisher.common.doOnCreateLifecycleEvent
import com.ably.tracking.demo.publisher.ui.widget.StyledTextField
import com.ably.tracking.demo.publisher.ui.widget.TextAlertDialog
import com.ably.tracking.demo.publisher.ui.widget.TextButton

@Composable
fun LoginScreen(viewModel: LoginViewModel = getViewModel()) {
    doOnCreateLifecycleEvent {
        viewModel.onCreated()
    }
    val state = viewModel.state.collectAsState()
    Scaffold(
        content = {
            LoginScreenContent(
                state = state.value,
                onDialogClose = viewModel::onFetchingSecretsFailedDialogClosed,
                onUsernameValueChange = viewModel::onUsernameChanged,
                onPasswordValueChange = viewModel::onPasswordChanged,
                onContinueClicked = viewModel::onContinueClicked
            )
        },
        modifier = Modifier.fillMaxSize(),
    )
}

@Preview
@Composable
fun LoginScreenContent(
    state: LoginScreenState = LoginScreenState(),
    onDialogClose: () -> Unit = {},
    onUsernameValueChange: (String) -> Unit = {},
    onPasswordValueChange: (String) -> Unit = {},
    onContinueClicked: () -> Unit = {}
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
        if (state.showProgress) {
            CircularProgressIndicator(
                modifier = Modifier.size(64.dp),
                strokeWidth = 6.dp
            )
        } else {
            UserCredentialsInputs(
                state = state,
                onUsernameValueChange = onUsernameValueChange,
                onPasswordValueChange = onPasswordValueChange,
                onContinueClicked = onContinueClicked
            )
        }
    }
}

@Preview
@Composable
fun UserCredentialsInputs(
    state: LoginScreenState = LoginScreenState(),
    onUsernameValueChange: (String) -> Unit = {},
    onPasswordValueChange: (String) -> Unit = {},
    onContinueClicked: () -> Unit = {}
) {
    StyledTextField(
        label = R.string.username_label,
        value = state.username,
        onValueChange = onUsernameValueChange
    )
    StyledTextField(
        label = R.string.password_label,
        value = state.password,
        visualTransformation = PasswordVisualTransformation(),
        onValueChange = onPasswordValueChange
    )
    TextButton(text = R.string.continue_button, onClick = onContinueClicked)
}