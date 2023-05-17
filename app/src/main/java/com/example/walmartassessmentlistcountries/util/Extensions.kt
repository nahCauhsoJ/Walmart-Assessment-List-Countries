package com.example.walmartassessmentlistcountries.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain_layer.model.CountryDisplayItem
import com.example.domain_layer.model.CountryResponseItem
import com.example.walmartassessmentlistcountries.R
import com.google.android.material.snackbar.Snackbar

fun <T : ViewModel> T.createFactory(): ViewModelProvider.Factory {
    val viewModel = this
    return object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = viewModel as T
    }
}

fun View.showErrorSnackbar(message: String, duration: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(this, message, duration).apply {
        view.setBackgroundColor(ContextCompat.getColor(context, R.color.snackbar_error_background))
        setTextColor(ContextCompat.getColor(context, R.color.snackbar_error_text))
        show()
    }
}

fun Context.isInternetAvailable(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
    return connectivityManager?.let { cm ->
        val networkCapabilities = cm.activeNetwork?.let { network ->
            cm.getNetworkCapabilities(network)
        }
        networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
    } ?: false
}

fun List<CountryResponseItem>.toSealed(): MutableList<CountryDisplayItem> = this.map {
    CountryDisplayItem.Item(it)
}.toMutableList()