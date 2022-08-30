package com.ably.tracking.demo.publisher.presentation.widget

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ably.tracking.demo.publisher.R

@Composable
fun TextAlertDialog(
    @StringRes title: Int,
    @StringRes text: Int,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                modifier = Modifier.padding(8.dp),
                text = stringResource(title)
            )
        },
        confirmButton = {
            StyledTextButton(
                text = R.string.ok,
                onClick = onDismiss
            )
        },
        text = {
            Text(
                modifier = Modifier.padding(8.dp),
                text = stringResource(text)
            )
        }
    )
}
