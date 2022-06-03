package com.ably.tracking.demo.publisher.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
        MainScreenContent(state.value) {
            viewModel.addOrder("pizza")
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreenContent(state: MainScreenState, onAddTrackable: () -> Unit) {
    LazyColumn {
        stickyHeader {
            Button(
                modifier = Modifier
                    .background(MaterialTheme.colors.background)
                    .fillMaxWidth()
                    .padding(8.dp),
                onClick = onAddTrackable
            ) {
                Text(text = "Add trackable")
            }
        }
        items(state.orders) { item ->
            OrderRow(order = item)
        }
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
    MainScreenContent(state) {}
}
