package com.ably.tracking.demo.publisher.common

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat

object PermissionHelper {

    fun ensureLocationPermission(activity: ComponentActivity, action: () -> Unit) {
        if (hasForegroundLocationPermission(activity)) {
            action()
        } else {
            requestLocationPermission(activity, action)
        }
    }

    private fun requestLocationPermission(activity: ComponentActivity, action: () -> Unit) {
        val requestPermissionLauncher =
            activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                onLocationPermissionResult(it, activity, action)
            }

        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun onLocationPermissionResult(
        isGranted: Boolean,
        activity: ComponentActivity,
        action: () -> Unit
    ) {
        if (isGranted) {
            action()
        } else {
            activity.finish()
        }
    }

    private fun hasForegroundLocationPermission(context: Context) =
        context.checkLocationPermission() == PackageManager.PERMISSION_GRANTED

    private fun Context.checkLocationPermission() =
        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
}
