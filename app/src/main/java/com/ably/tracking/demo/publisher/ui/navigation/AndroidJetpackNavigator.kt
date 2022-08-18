package com.ably.tracking.demo.publisher.ui.navigation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.ably.tracking.demo.publisher.R
import java.util.ArrayList

class AndroidJetpackNavigator(private val activity: Activity) : Navigator {

    lateinit var navController: NavController

    override fun openMain() {
        val navOptions = NavOptions.Builder().setPopUpTo(Routes.Login.path, true).build()
        navController.navigate(Routes.Main.path, navOptions)
    }

    override fun openSettings() {
        navController.navigate(Routes.Settings.path)
    }

    override fun goBack() {
        navController.popBackStack()
    }

    override fun share(logShareHeader: Int, shareFiles: List<Uri>) {
        val header = activity.getString(logShareHeader)
        val intent = Intent(Intent.ACTION_SEND_MULTIPLE)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.type = "text/html"
        intent.putExtra(Intent.EXTRA_SUBJECT, header)
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, ArrayList(shareFiles))

        val title = activity.getString(R.string.log_share_dialog_title)
        activity.startActivity(Intent.createChooser(intent, title))
    }
}
