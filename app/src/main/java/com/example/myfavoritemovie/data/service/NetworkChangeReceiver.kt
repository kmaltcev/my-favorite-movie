package com.example.myfavoritemovie.data.service


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast

const val NO_ACTIVE_NETWORK = 0
const val CELLULAR_NETWORK = 1
const val WIFI_NETWORK = 2

class NetworkChangeReceiver : BroadcastReceiver() {
    private var lastAvailable = 0
    override fun onReceive(context: Context?, intent: Intent?) {
        val currNetworkStatus = checkNetwork(context)
        when (currNetworkStatus) {
            NO_ACTIVE_NETWORK ->
                if (lastAvailable != NO_ACTIVE_NETWORK)
                    Toast.makeText(context, "Network Unavailable", Toast.LENGTH_SHORT).show()
            CELLULAR_NETWORK ->
                if (lastAvailable != CELLULAR_NETWORK)
                    Toast.makeText(context, "Cellular Network Available", Toast.LENGTH_SHORT).show()
            WIFI_NETWORK ->
                if (lastAvailable != WIFI_NETWORK)
                    Toast.makeText(context, "Wifi Network Available", Toast.LENGTH_SHORT).show()
        }
        lastAvailable = currNetworkStatus
    }

    private fun checkNetwork(context: Context?): Int {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = cm.activeNetwork ?: return NO_ACTIVE_NETWORK
        val actNw = cm.getNetworkCapabilities(networkCapabilities) ?: return NO_ACTIVE_NETWORK
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> WIFI_NETWORK
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> CELLULAR_NETWORK
            else -> NO_ACTIVE_NETWORK
        }
    }
}



