package com.example.currencyquotedoge

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import okhttp3.*
import java.io.IOException

class QuotesService : Service() {

    private val TAG = "ServiceExample"
    val URL =
        "https://min-api.cryptocompare.com/data/price?fsym=DOGE&tsyms=USD,EUR,RUB&api_key=${R.string.api_key}"
    val okHttpClient = OkHttpClient()
    val local = Intent()

    override fun onCreate() {
        Log.i(TAG, "Service onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG, "Service onStartCommand $startId")

        val request: Request = Request.Builder().url(URL).build()
        okHttpClient.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                Log.d("mytag", "Failed call: $e")
            }

            override fun onResponse(call: Call?, response: Response?) {
                val json = response?.body()?.string()
                // val quotes: Quotes = Gson().fromJson(json, Quotes::class.java)
                Log.d("mytag", "from service: $json")

                local.putExtra("json", json)
                local.action = "android.intent.action.TIME_TICK"
            }
        })

        this.sendBroadcast(local)
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.i(TAG, "Service onBind")
        return null
    }

    override fun onDestroy() {
        Log.i(TAG, "Service onDestroy")
    }
}