package com.teumteum.teumteum.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities

class NetworkManager {

    companion object {
        fun checkNetworkState(context: Context): Boolean {
            val connectivityManager: ConnectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val network: Network = connectivityManager.activeNetwork ?: return false
            val actNetwork: NetworkCapabilities =
                connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                else -> false
            }
        }
    }

}