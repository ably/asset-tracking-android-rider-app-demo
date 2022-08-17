package com.ably.tracking.demo.publisher.ui.main

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import com.ably.tracking.demo.publisher.R
import com.ably.tracking.demo.publisher.common.PermissionHelper


fun requestLocationPermission(activity: ComponentActivity, viewModel: MainViewModel) {
    PermissionHelper.ensureLocationPermission(
        activity,
        onDenied = { showPermissionRequiredDialog(activity) }) {
        viewModel.onLocationPermissionGranted()
    }
}

private fun showPermissionRequiredDialog(activity: ComponentActivity) {
    showOkDialog(
        activity = activity,
        title = R.string.location_permission_denied_dialog_title,
        message = R.string.location_permission_denied_dialog_message,
        onOk = { navigateToAppSettingsScreen(activity) }
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

private fun navigateToAppSettingsScreen(activity: ComponentActivity) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    val uri: Uri = Uri.fromParts("package", activity.packageName, null)
    intent.data = uri
    activity.startActivity(intent)
}
