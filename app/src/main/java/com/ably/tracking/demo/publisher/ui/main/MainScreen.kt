package com.ably.tracking.demo.publisher.ui.main

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ably.tracking.demo.publisher.R
import com.ably.tracking.demo.publisher.ui.debug.DebugScreenContent
import com.ably.tracking.demo.publisher.ui.widget.TextButton

@Composable
fun MainScreen(viewModel: MainViewModel, openSettings: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                // Provide Title
                title = {
                    Text(text = stringResource(R.string.main_screen_title), color = Color.White)
                },
                actions = {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = stringResource(R.string.debug_screen_back_description),
                        modifier = Modifier.clickable(onClick = openSettings),
                        tint = Color.White
                    )
                }
            )
        },
        modifier = Modifier.fillMaxSize(),
        content = {
            val state = viewModel.state.collectAsState()
            MainScreenContent(state.value) { name, destinationLatitude, destinationLongitude ->
                viewModel.addOrder(name, destinationLatitude, destinationLongitude)
            }
        },
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreenContent(state: MainScreenState, onAddTrackable: (String, String, String) -> Unit) {
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }
    LazyColumn {
        stickyHeader {
            TextButton(text = R.string.add_order_button) {
                setShowDialog(true)
            }
        }
        items(state.orders) { item ->
            OrderRow(order = item)
        }
    }
    if (showDialog) {
        AddOrderDialog(setShowDialog, onAddTrackable)
    }
}

@Composable
fun OrderRow(order: Order) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text = order.name)
        Text(text = stringResource(id = order.state))
        Column {
            TextButton(text = R.string.track_order_button, onClick = order.onTrackClicked)
            TextButton(text = R.string.remove_order_button, onClick = order.onRemoveClicked)
        }
    }
}

@Composable
fun AddOrderDialog(setShowDialog: (Boolean) -> Unit, onConfirm: (String, String, String) -> Unit) {
    var orderName by rememberSaveable { mutableStateOf("") }
    var destinationLatitude by rememberSaveable { mutableStateOf("51.1065859") }
    var destinationLongitude by rememberSaveable { mutableStateOf("17.0312766") }

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
            TextButton(
                text = R.string.add_order_dialog_confirm_button
            ) {
                // Change the state to close the dialog
                setShowDialog(false)
                onConfirm(orderName, destinationLatitude, destinationLongitude)
            }
        },
        text = {
            Column {
                TextInput(orderName, R.string.add_order_dialog_order_name_hint) { orderName = it }
                TextInput(
                    destinationLatitude,
                    R.string.add_order_dialog_order_latitude_hint
                ) { destinationLatitude = it }
                TextInput(
                    destinationLongitude,
                    R.string.add_order_dialog_order_longitude_hint
                ) { destinationLongitude = it }
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

@Preview(showBackground = true)
@Composable
fun OrderRowPreview() {
    val order = Order(name = "Burger", R.string.trackable_state_online, {}, {})
    OrderRow(order = order)
}

@Preview(showBackground = true)
@Composable
fun MainScreenContentPreview() {
    val state = MainScreenState(
        listOf(
            Order(name = "Burger", R.string.trackable_state_online, {}, {}),
            Order(name = "Pizza", R.string.trackable_state_offline, {}, {}),
            Order(name = "Sushi", R.string.trackable_state_publishing, {}, {})
        )
    )
    MainScreenContent(state) { _, _, _ -> }
}

@Preview(showBackground = true)
@Composable
fun AddOrderDialogPreview() {
    AddOrderDialog({}) { _, _, _ -> }
}
