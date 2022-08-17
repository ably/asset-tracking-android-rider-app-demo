package com.ably.tracking.demo.publisher.ui.main

import android.Manifest
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.ably.tracking.demo.publisher.PublisherService
import com.ably.tracking.demo.publisher.R
import com.ably.tracking.demo.publisher.common.doOnCreateLifecycleEvent
import com.ably.tracking.demo.publisher.ui.widget.TextButton
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

import org.koin.androidx.compose.getViewModel

@ExperimentalPermissionsApi
@Composable
fun MainScreen(
    activity: ComponentActivity,
    viewModel: MainViewModel = getViewModel()
) {

    val locationPermissionState = rememberPermissionState(
        permission = Manifest.permission.ACCESS_FINE_LOCATION,
        onPermissionResult = { granted ->
            onLocationPermissionResult(granted, activity, viewModel)
        }
    )

    doOnCreateLifecycleEvent {
        locationPermissionState.launchPermissionRequest()
    }

    doOnCreateLifecycleEvent {
        ContextCompat.startForegroundService(
            activity,
            Intent(activity, PublisherService::class.java)
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.main_screen_title), color = Color.White)
                },
                actions = {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = stringResource(R.string.settings_screen_back_description),
                        modifier = Modifier.clickable(onClick = viewModel::onSettingsClicked),
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
fun OrderRow(order: OrderViewItem) {
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

@Preview(showBackground = true)
@Composable
fun OrderRowPreview() {
    val order = OrderViewItem(name = "Burger", R.string.trackable_state_online, {}, {})
    OrderRow(order = order)
}

@Preview(showBackground = true)
@Composable
fun MainScreenContentPreview() {
    val state = MainScreenState(
        listOf(
            OrderViewItem(name = "Burger", R.string.trackable_state_online, {}, {}),
            OrderViewItem(name = "Pizza", R.string.trackable_state_offline, {}, {}),
            OrderViewItem(name = "Sushi", R.string.trackable_state_publishing, {}, {})
        )
    )
    MainScreenContent(state) { _, _, _ -> }
}

@Preview(showBackground = true)
@Composable
fun AddOrderDialogPreview() {
    AddOrderDialog({}) { _, _, _ -> }
}
