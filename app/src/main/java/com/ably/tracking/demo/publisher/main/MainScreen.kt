package com.ably.tracking.demo.publisher.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ably.tracking.demo.publisher.R

@Composable
fun MainScreen(viewModel: MainViewModel) {
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        val state = viewModel.state.collectAsState()
        MainScreenContent(state.value) { name, destinationLatitude, destinationLongitude ->
            viewModel.addOrder(name, destinationLatitude, destinationLongitude)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreenContent(state: MainScreenState, onAddTrackable: (String, String, String) -> Unit) {
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }
    LazyColumn {
        stickyHeader {
            Button(
                modifier = Modifier
                    .background(MaterialTheme.colors.background)
                    .fillMaxWidth()
                    .padding(8.dp),
                onClick = {
                    setShowDialog(true)
                }
            ) {
                Text(text = "Add order")
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
        Button(onClick = order.onTrackClicked) {
            Text(text = "Track")
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
                text = "Enter order details"
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    // Change the state to close the dialog
                    setShowDialog(false)
                    onConfirm(orderName, destinationLatitude, destinationLongitude)
                },
            ) {
                Text("Confirm")
            }
        },
        text = {
            Column {
                TextField(
                    modifier = Modifier.padding(8.dp),
                    value = orderName,
                    onValueChange = {
                        orderName = it
                    },
                    label = { Text("Order name") }
                )
                TextField(
                    modifier = Modifier.padding(8.dp),
                    value = destinationLatitude,
                    onValueChange = {
                        destinationLatitude = it
                    },
                    label = { Text("Destination latitude") }
                )
                TextField(
                    modifier = Modifier.padding(8.dp),
                    value = destinationLongitude,
                    onValueChange = {
                        destinationLongitude = it
                    },
                    label = { Text("Destination longitude") }
                )
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun OrderRowPreview() {
    val order = Order(name = "Burger", R.string.trackable_state_online) {}
    OrderRow(order = order)
}

@Preview(showBackground = true)
@Composable
fun MainScreenContentPreview() {
    val state = MainScreenState(listOf(
        Order(name = "Burger", R.string.trackable_state_online) {},
        Order(name = "Pizza", R.string.trackable_state_offline) {},
        Order(name = "Sushi", R.string.trackable_state_publishing) {}
    ))
    MainScreenContent(state) { _, _, _ -> }
}

@Preview(showBackground = true)
@Composable
fun AddOrderDialogPreview() {
    AddOrderDialog({}) { _, _, _ -> }
}
