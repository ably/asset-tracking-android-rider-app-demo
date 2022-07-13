package com.ably.tracking.demo.publisher.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
                Text(text = stringResource(id = R.string.add_order_button))
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
            Button(onClick = order.onTrackClicked) {
                Text(text = stringResource(id = R.string.track_order_button))
            }
            Button(onClick = order.onRemoveClicked) {
                Text(text = stringResource(id = R.string.remove_order_button))
            }
        }
    }
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
