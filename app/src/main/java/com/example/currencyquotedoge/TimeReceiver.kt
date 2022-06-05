package com.example.currencyquotedoge

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class TimeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val json = (intent.getStringExtra("json"))
        if (json != null) {
            Log.d("mytag", json)
        }
        (context as MainActivity).timeUpdate(json)
    }
}