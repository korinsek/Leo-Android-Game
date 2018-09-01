package com.mag.denis.game.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import com.mag.denis.game.R
import dagger.android.DaggerService

class MusicService : DaggerService(), MusicView {

    private lateinit var player: MediaPlayer

    override fun onCreate() {
        super.onCreate()
        player = MediaPlayer.create(this, R.raw.melody)
        player.isLooping = true
        player.setVolume(0.3f, 0.3f)
        startForegroundService()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        player.start()
        return Service.START_STICKY
    }

    override fun onDestroy() {
        player.stop()
        player.release()
    }

    private fun startForegroundService() {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(getText(R.string.app_name))
                .setContentText(getText(R.string.app_name))

        notification.priority = NotificationCompat.PRIORITY_MIN
        startForeground(FOREGROUND_NOTIFICATION_ID, notification.build())
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    companion object {
        private const val FOREGROUND_NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "programing_game_foreground"

        fun newIntent(context: Context): Intent {
            return Intent(context, MusicService::class.java)
        }
    }
}
