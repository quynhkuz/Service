package com.example.testreminderapp

import android.app.*
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.net.Uri
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import java.util.*

class MyService : Service() {


    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.d("AAA","Service")

        var mediaPlayer :MediaPlayer= MediaPlayer.create(this,R.raw.batoi)
        mediaPlayer.start()

        pusnotification2()

        return START_NOT_STICKY
    }


    private fun pusnotification2() {

        val resultIntent = Intent(this, MainActivity::class.java)
        resultIntent.putExtra("idReminder","123456")
// Create the TaskStackBuilder
        val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(this).run {
            // Add the intent, which inflates the back stack
            addNextIntentWithParentStack(resultIntent)
            // Get the PendingIntent containing the entire back stack
            getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        }

        val sound = Uri.parse("android.resource://" + packageName + "/" + R.raw.batoi)
        val bm = BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher_foreground)
        val bm2 = BitmapFactory.decodeResource(this.resources, R.drawable.pikachu)
        val notification: Notification =
            NotificationCompat.Builder(this, MyApplication.CHANNEL_ID)
                .setContentText("Hello2")
                .setContentTitle("Notification2")
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bm2).bigLargeIcon(null))
                .setLargeIcon(bm)
                .setSound(sound) //.setLargeIcon(bm)
                .setContentIntent(resultPendingIntent)
                //.setColor(getResources().getColor(R.color.teal_200))
                .build()

//        startForeground(getnotification(),notification)
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager?.notify(getnotification(), notification)
    }

    private fun getnotification(): Int {
        return Date().time.toInt()
    }


}