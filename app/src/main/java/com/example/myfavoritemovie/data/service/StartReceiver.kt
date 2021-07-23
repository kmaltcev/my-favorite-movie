package com.example.myfavoritemovie.data.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

class StartReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED && getServiceState(context) == ServiceState.STARTED) {
            Intent(context, UpcomingMoviesReceiver::class.java).also {
                it.action = Actions.START.name
                context.startForegroundService(it)
            }
        }
    }
}