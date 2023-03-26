package com.example.testreminderapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.Toast

class BoundService :Service() {
    var mediaPlayer : MediaPlayer? = null
    var myBind = MyBind()

    inner class MyBind : Binder() {

        fun getmyService():BoundService
        {
            return this@BoundService
        }
    }

    override fun onCreate() {
        Log.d("AAA","onCreate")
        super.onCreate()
    }

    override fun onBind(p0: Intent?): IBinder? {

        Log.d("AAA","onBind")
        return myBind
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d("AAA","onUnbind")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        var it = Intent(this,MyReciver::class.java)
        var pendingIntent = PendingIntent.getBroadcast(this,0,it,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        var alarmManager  = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
       alarmManager.cancel(pendingIntent)
        Log.d("AAA","cancel servicer")
    }

    fun starMediaplayer()
    {

        var it = Intent(this,MyReciver::class.java)
        var pendingIntent = PendingIntent.getBroadcast(this,0,it,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        var alarmManager  = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis() + 10000,pendingIntent)
        Log.d("AAA","star servicer")
    }
}