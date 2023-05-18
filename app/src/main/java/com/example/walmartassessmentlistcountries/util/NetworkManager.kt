package com.example.walmartassessmentlistcountries.util

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest

class NetworkManager(
    onAvailable: () -> Unit,
    onCapabilitiesChanged: () -> Unit = {},
    onLost: () -> Unit
) {
    private var networkCallback = object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            onAvailable()
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            //val unmetered = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)
            onCapabilitiesChanged()
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            onLost()
        }
    }

    fun startCallback(manager: ConnectivityManager) {
        manager.requestNetwork(networkRequest, networkCallback)
    }

    companion object {
        private val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            //.addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
    }
}