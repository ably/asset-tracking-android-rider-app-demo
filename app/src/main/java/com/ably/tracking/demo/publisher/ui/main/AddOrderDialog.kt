package com.ably.tracking.demo.publisher.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ably.tracking.demo.publisher.R
import com.ably.tracking.demo.publisher.common.canParseToDouble
import com.ably.tracking.demo.publisher.ui.widget.StyledTextButton
import com.ably.tracking.demo.publisher.ui.widget.StyledTextField

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
            StyledTextButton(
                isEnabled = orderName.canParseToDouble(),
                text = R.string.add_order_dialog_confirm_button,
                onClick = {
                    setShowDialog(false)
                    onConfirm(orderName)
                }
            )
        },
        text = {
            Column {
                StyledTextField(
                    label = R.string.add_order_dialog_order_name_hint,
                    value = orderName,
                    onValueChange = { orderName = it }
                )
            }
        },
    )
}
