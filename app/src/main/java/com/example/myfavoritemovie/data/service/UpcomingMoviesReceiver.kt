package com.example.myfavoritemovie.data.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.NavDeepLinkBuilder
import com.example.myfavoritemovie.R
import com.example.myfavoritemovie.app.app
import com.example.myfavoritemovie.ui.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UpcomingMoviesReceiver : Service() {
    private var wakeLock: PowerManager.WakeLock? = null
    private var isServiceStarted = false

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

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Actions.START.name -> startService()
            Actions.STOP.name -> stopService()
            else -> Log.wtf("MY_APP_SERVICE", "No action in the received intent")
        }
        return super.onStartCommand(intent, flags, startId)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotification(): Notification {
        val notificationChannelId = "UPCOMING MOVIES CHANNEL"
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            notificationChannelId,
            "Upcoming Movies Service notifications channel",
            NotificationManager.IMPORTANCE_DEFAULT
        ).let {
            it.description = "Upcoming Movies Service channel"
            it.enableLights(true)
            it.lightColor = Color.RED
            it.enableVibration(true)
            it.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            it
        }
        notificationManager.createNotificationChannel(channel)

        GlobalScope.launch(Dispatchers.IO) {
            val upcomingMovies = applicationContext.app.searchModule.searchUpcomingAction("RU")
            Log.wtf("MY_APP", "$upcomingMovies")
        }

        val pendingIntent = NavDeepLinkBuilder(applicationContext)
            .setComponentName(MainActivity::class.java)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.upcomingFragment)
//            .setArguments(bundle)
            .createPendingIntent()


        val builder: Notification.Builder = Notification.Builder(this, notificationChannelId)

        return builder
            .setContentTitle("Upcoming Next Week")
            .setContentText("Upcoming Movies service working")
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setTicker("Ticker text")
            .build()
    }

    private fun startService() {
        if (isServiceStarted) return
        isServiceStarted = true
        setServiceState(this, ServiceState.STARTED)

        wakeLock = (getSystemService(Context.POWER_SERVICE) as PowerManager).run {
            newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "ReleaseDateReceiver::lock").apply {
                acquire(10 * 60 * 1000L /*10 minutes*/)
            }
        }
    }

    private fun stopService() {
        Log.wtf("MY_APP_SERVICE", "Stopping the foreground service")
        Toast.makeText(this, "Service stopping", Toast.LENGTH_SHORT).show()
        try {
            wakeLock?.let {
                if (it.isHeld) {
                    it.release()
                }
            }
            stopForeground(true)
            stopSelf()
        } catch (e: Exception) {
            Log.wtf("MY_APP_SERVICE", "Service stopped without being started: ${e.message}")
        }
        isServiceStarted = false
        setServiceState(this, ServiceState.STOPPED)
    }
}