package com.ably.tracking.demo.publisher.common

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ably.tracking.demo.publisher.ably.AssetTracker
import com.ably.tracking.demo.publisher.main.MainViewModel
import kotlinx.coroutines.Dispatchers

class ViewModelProviderFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when (modelClass) {
            MainViewModel::class.java -> provideMainViewModel() as T
            else -> throw UnsupportedOperationException("no factory method defined for $modelClass")
        }

    private fun provideMainViewModel(): MainViewModel {
        val assetTracker = AssetTracker.build(context)
        return MainViewModel(assetTracker, Dispatchers.Main)
    }
}
