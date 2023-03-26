package com.example.testreminderapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import java.util.*

class ForegroindService: Service() {

    var check=false


    override fun onBind(p0: Intent?): IBinder? {
        Log.d("AAA"," onBind")
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("AAA","onCreate")
        check = true
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("AAA"," onStartCommand")


        var s = intent?.extras
        var id = s?.getInt("key",0)
        Log.d("AAA",id.toString())

        if (id!! > 0)
        {
            var it = Intent(this,MyReciver::class.java)
            var pendingIntent = PendingIntent.getBroadcast(this,id,it,PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            var alarmManager  = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis() + 10000,pendingIntent)
        }
        else
        {
            var it = Intent(this,MyReciver::class.java)
            var pendingIntent = PendingIntent.getBroadcast(this,0,it,PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            var alarmManager  = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(pendingIntent)
        }


        if (check)
        {
            pushNotification("any body here")
            check = false
        }




        return START_NOT_STICKY
    }

    private fun pushNotification(data:String) {

        //Thiết lập một intent chờ xử lý
        val notifyIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val notifyPendingIntent = PendingIntent.getActivity(
            this, 0, notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        var builder = NotificationCompat.Builder(this, MyApplication.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_local_post_office_24)
            .setContentTitle(data)
            .setContentText("Much longer text that cannot fit one line...")
            .setStyle(
                NotificationCompat.BigTextStyle()
                .bigText("Much longer text that cannot fit one line..."))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(notifyPendingIntent)
            .build()
        //startForeground service
        startForeground(getNoticationID(),builder)

//        with(NotificationManagerCompat.from(this)) {
//            notify(getNoticationID(), builder.build())
//        }
    }

    private fun getNoticationID(): Int {
        return Date().time.toInt()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("AAA"," onDestroy")
    }
}