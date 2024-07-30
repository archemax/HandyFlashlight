package com.arche.handyflashlight.flashlight

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.arche.handyflashlight.R
import com.arche.handyflashlight.MainActivity
import com.arche.handyflashlight.user_interface.PowerButtonReceiver


class FlashlightService : Service() {

    private lateinit var powerButtonReceiver: PowerButtonReceiver

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()

        //registering power button receiver
        powerButtonReceiver = PowerButtonReceiver(applicationContext)
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_SCREEN_ON)
        filter.addAction(Intent.ACTION_SCREEN_OFF)
        registerReceiver(powerButtonReceiver, filter)
    }

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {

        if (intent?.action == ACTION_STOP_SERVICE){
            showStopToast()
            stopSelf()
            return START_NOT_STICKY
        }

        val notificationIntent = Intent(this, MainActivity::class.java)
        val stopServiceIntent = Intent(this, FlashlightService::class.java).apply {
            action = ACTION_STOP_SERVICE
        }

        val tapNotificationPendingIntent = PendingIntent
            .getActivity(
                this,
                0,
                notificationIntent,
                PendingIntent.FLAG_IMMUTABLE
            )

        val closeAppIntent = PendingIntent.getService(
            this,
            0,
            stopServiceIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Flashlight Service")
            .setContentText("Double click power button")
            .setSmallIcon(R.drawable.fflashlight_icon) // Use your app icon here
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setContentIntent(tapNotificationPendingIntent)
            .setOngoing(true)
            .addAction(R.drawable.close_icon_24, "STOP APP", closeAppIntent)
            .build()

        startForeground(NOTIFICATION_ID, notification)

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(powerButtonReceiver)

    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Flashlight Service",
                NotificationManager.IMPORTANCE_HIGH
            )

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    companion object {
        const val CHANNEL_ID = "Flashlight Service Channel"
        const val NOTIFICATION_ID = 1
        const val ACTION_STOP_SERVICE = "ACTION_STOP_SERVICE"
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun showStopToast() {
        Toast.makeText(applicationContext, "Service stopped", Toast.LENGTH_SHORT).show()
    }


}






