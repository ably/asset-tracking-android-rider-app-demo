package com.ably.tracking.demo.publisher.common

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.ably.tracking.demo.publisher.R
import com.google.android.datatransport.runtime.backends.BackendResponse.ok


object PermissionHelper {

    fun ensureLocationPermission(activity: ComponentActivity) =
        ensureForegroundLocationPermission(activity) {
            if (!hasBackgroundLocationPermission(activity)) {
                activity.showLocationPermissionSettingsDialog()
            }
        }

    private fun ensureForegroundLocationPermission(
        activity: ComponentActivity,
        action: () -> Unit
    ) {
        val requestPermissionLauncher =
            activity.registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    action()
                } else {
                    activity.finish()
                }
            }
        if (hasForegroundLocationPermission(activity)) {
            action()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun hasForegroundLocationPermission(context: Context) =
        context.hasLocationPermission(Manifest.permission.ACCESS_FINE_LOCATION)

    fun hasBackgroundLocationPermission(context: Context) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            context.hasLocationPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        } else {
            true
        }

    private fun Context.hasLocationPermission(permission: String) =
        ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

    private fun Context.showLocationPermissionSettingsDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.background_location_permission_dialog_title)
            .setMessage(R.string.background_location_permission_dialog_text)
            .setPositiveButton(R.string.ok) { _, _ -> launchSettings() }
            .show()
    }

    private fun Context.launchSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }
}
