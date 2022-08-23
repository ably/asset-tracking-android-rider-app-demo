package com.ably.tracking.demo.publisher.ui.main

import android.app.AlertDialog
import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import com.ably.tracking.demo.publisher.R
import com.ably.tracking.demo.publisher.ui.navigation.Navigator

fun onLocationPermissionResult(
    granted: Boolean,
    activity: ComponentActivity,
    navigator: Navigator,
    viewModel: MainViewModel
) {
    if (granted) {
        viewModel.onLocationPermissionGranted()
    } else {
        showPermissionRequiredDialog(activity, navigator)
    }
}

private fun showPermissionRequiredDialog(activity: ComponentActivity, navigator: Navigator) {
    showOkDialog(
        activity = activity,
        title = R.string.location_permission_denied_dialog_title,
        message = R.string.location_permission_denied_dialog_message,
        onOk = { navigator.navigateToAppSettingsScreen() }
    )
}

private fun showOkDialog(
    activity: ComponentActivity,
    @StringRes title: Int,
    @StringRes message: Int,
    onOk: () -> Unit
) {
    AlertDialog.Builder(activity)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(R.string.ok) { _, _ -> onOk() }
        .setCancelable(false)
        .show()
}
