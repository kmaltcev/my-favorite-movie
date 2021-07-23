package com.example.myfavoritemovie.data.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.navigation.NavDeepLinkBuilder
import com.example.myfavoritemovie.R
import com.example.myfavoritemovie.ui.MainActivity

class UpcomingMoviesReceiver : Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        Log.wtf("MY_APP_SERVICE", "The service has been created")
        val notification = createNotification()
        startForeground(1, notification)
        super.onCreate()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotification(): Notification {


        val notificationChannelId = "UPCOMING_MOVIES_CHANNEL"
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            notificationChannelId,
            "Upcoming Movies Service notifications channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)

        val pendingIntent = NavDeepLinkBuilder(applicationContext)
            .setComponentName(MainActivity::class.java)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.upcomingFragment)
            .createPendingIntent()

        val builder: Notification.Builder = Notification.Builder(this, notificationChannelId)

        return builder
            .setContentTitle("Upcoming Next Week")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentText("Hey there! You have new movies coming next week")
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setTicker("Ticker text")
            .build()
    }
}