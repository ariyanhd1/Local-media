package com.jeet_studio.localstream4k.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.jeet_studio.localstream4k.MainActivity
import com.jeet_studio.localstream4k.server.LocalHttpServer
import java.io.File

class ServerForegroundService : Service() {
    private var server: LocalHttpServer? = null

    companion object {
        const val CHANNEL_ID = "LocalStreamServerChannel"
        const val NOTIFICATION_ID = 1001
        const val ACTION_STOP = "com.jeet_studio.localstream4k.STOP_SERVER"
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == ACTION_STOP) {
            stopServer()
            stopSelf()
            return START_NOT_STICKY
        }

        createNotificationChannel()
        val notification = buildNotification("Running at http://192.168.31.217:8080")
        startForeground(NOTIFICATION_ID, notification)

        startServer()
        return START_STICKY
    }

    private fun startServer() {
        val rootDir = getExternalFilesDir(null) ?: filesDir
        server = LocalHttpServer(8080, rootDir)
        try {
            server?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun stopServer() {
        server?.stop()
        server = null
    }

    override fun onDestroy() {
        stopServer()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "LocalStream Server Service",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun buildNotification(url: String): Notification {
        val pendingIntent = PendingIntent.getActivity(
            this, 0,
            Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("LocalStream Pro is Active")
            .setContentText(url)
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()
    }
}
