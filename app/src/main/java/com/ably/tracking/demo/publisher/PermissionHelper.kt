package com.ably.tracking.demo.publisher

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat

object PermissionHelper {

    private const val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION

    fun ensureLocationPermission(activity: ComponentActivity, action: () -> Unit) {
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
        if (activity.hasLocationPermission()) {
            action()
        }
        else {
            requestPermissionLauncher.launch(locationPermission)
        }
    }

    private fun Context.hasLocationPermission() =
        ActivityCompat.checkSelfPermission(
            this,
            locationPermission
        ) == PackageManager.PERMISSION_GRANTED
}
