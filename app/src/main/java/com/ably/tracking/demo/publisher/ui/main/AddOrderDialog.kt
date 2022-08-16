package com.ably.tracking.demo.publisher.ui.main

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ably.tracking.demo.publisher.R

@Composable
fun AddOrderDialog(setShowDialog: (Boolean) -> Unit, onConfirm: (String) -> Unit) {
    var orderName by rememberSaveable { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = {
            setShowDialog(false)
        },
        title = {
            Text(
                modifier = Modifier.padding(8.dp),
                text = stringResource(id = R.string.add_order_dialog_title)
            )
        },
        confirmButton = {
            Button(
                enabled = orderName.isNotBlank(),
                onClick = {
                    // Change the state to close the dialog
                    setShowDialog(false)
                    onConfirm(orderName)
                },
            ) {
                Text(stringResource(id = R.string.add_order_dialog_confirm_button))
            }
        },
        text = {
            Column {
                TextInput(orderName, R.string.add_order_dialog_order_name_hint) { orderName = it }
            }
        },
    )
}

@Composable
private fun TextInput(
    orderName: String,
    @StringRes labelRes: Int,
    onValueChange: (String) -> Unit
) {
    TextField(
        modifier = Modifier.padding(8.dp),
        value = orderName,
        onValueChange = onValueChange,
        label = {
            Text(
                stringResource(id = labelRes)
            )
        }
    )
}
