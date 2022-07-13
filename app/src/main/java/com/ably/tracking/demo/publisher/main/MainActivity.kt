package com.ably.tracking.demo.publisher.main

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.ably.tracking.demo.publisher.PublisherService
import com.ably.tracking.demo.publisher.R
import com.ably.tracking.demo.publisher.common.PermissionHelper
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MainScreen(viewModel) }
        ContextCompat.startForegroundService(this, Intent(this, PublisherService::class.java))

        PermissionHelper.ensureLocationPermission(this, this::showPermissionRequiredDialog) {
            viewModel.onLocationPermissionGranted()
        }
    }

    private fun showPermissionRequiredDialog() {
        showOkDialog(
            title = R.string.location_permission_denied_dialog_title,
            message = R.string.location_permission_denied_dialog_message,
            onOk = this::navigateToAppSettings
        )
    }

    private fun showOkDialog(@StringRes title: Int, @StringRes message: Int, onOk: () -> Unit) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(R.string.ok) { _, _ -> onOk() }
            .setCancelable(false)
            .show()
    }

    private fun navigateToAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }
}
