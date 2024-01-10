package com.teumteum.teumteum.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

object NetworkStatus {
    const val TYPE_WIFI = 1
    const val TYPE_MOBILE = 2
    const val TYPE_NOT_CONNECTED = 3

    fun getConnectivityStatus(context: Context): Int {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = manager.activeNetworkInfo

        return when {
            networkInfo?.type == ConnectivityManager.TYPE_MOBILE -> TYPE_MOBILE
            networkInfo?.type == ConnectivityManager.TYPE_WIFI -> TYPE_WIFI
            else -> TYPE_NOT_CONNECTED
        }
    }
}
