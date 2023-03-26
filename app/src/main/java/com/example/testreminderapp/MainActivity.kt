package com.example.testreminderapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var myService: BoundService
    var isServiceconnection: Boolean = false

    var serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, iBind: IBinder?) {
            var myBind: BoundService.MyBind = iBind as BoundService.MyBind
            myService = myBind.getmyService()
            myService.starMediaplayer()

            isServiceconnection = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            isServiceconnection = false
        }
    }


    lateinit var btn: Button
    lateinit var service: Button
    lateinit var cancel : Button
    lateinit var txt :EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var s = intent.extras
        var id = s?.getString("idReminder")
        Log.d("AAA","idNote " +id.toString())

        txt = findViewById(R.id.txt_code)
        service = findViewById(R.id.btn_bound)
        btn = findViewById(R.id.btn_reminder)
        cancel = findViewById(R.id.btn_cencel)

        btn.setOnClickListener {

            stopForeService()

//            var it = Intent(this,MyReciver::class.java)
//            var pendingIntent = PendingIntent.getBroadcast(this,0,it,PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
//            var alarmManager  = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//            alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis() + 10000,pendingIntent)
//            Toast.makeText(this,"Reminder Susces",Toast.LENGTH_LONG).show()
        }


        service.setOnClickListener {
            var code = txt.text.toString().trim()
            Toast.makeText(this, "star service", Toast.LENGTH_LONG).show()
            starForeService(code.toInt())
        }


        cancel.setOnClickListener {
            starForeService(-5)
            Toast.makeText(this, "clear service", Toast.LENGTH_LONG).show()
        }

    }

    private fun stopForeService() {
        var intern = Intent(this, ForegroindService::class.java)
        stopService(intern)
    }

    private fun starForeService(s :Int) {

        var intern = Intent(this, ForegroindService::class.java)
        intern.putExtra("key", s)
        startService(intern)
    }


    private fun starBoundService() {
        Log.d("AAA", "star")
        var intent = Intent(this, BoundService::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)

    }

    private fun stopBoundService() {
        if (isServiceconnection) {
            unbindService(serviceConnection)
            isServiceconnection = false
        }
    }
}