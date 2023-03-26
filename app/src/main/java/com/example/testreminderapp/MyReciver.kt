package com.example.testreminderapp

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class MyReciver : BroadcastReceiver() {
    override fun onReceive(context: Context?, p1: Intent?) {

        Log.d("AAA","broadcastreciver")

        var it = Intent(context,MyService::class.java)
        context?.startService(it)

    }
}