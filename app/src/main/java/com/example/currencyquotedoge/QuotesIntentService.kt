package com.example.currencyquotedoge

import android.app.IntentService
import android.content.Intent
import android.content.Context
import android.util.Log
import okhttp3.*
import java.io.IOException

private const val ACTION_FOO = "android.intent.action.TIME_TICK"


private const val EXTRA_PARAM1 = "json"

class QuotesIntentService : IntentService("QuotesIntentService") {

    val URL =
        "https://min-api.cryptocompare.com/data/price?fsym=DOGE&tsyms=USD,EUR,RUB&api_key=${R.string.api_key}"
    val okHttpClient = OkHttpClient()
    lateinit var param1: String

    override fun onStart(intent: Intent?, startId: Int) {
        val request: Request = Request.Builder().url(URL).build()
        okHttpClient.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                Log.d("mytag", "Failed call: $e")
            }

            override fun onResponse(call: Call?, response: Response?) {
                param1 = response?.body()?.string().toString()
                Log.d("mytag", "from service: $param1")
            }
        })
    }

    override fun onHandleIntent(intent: Intent?) {
        when (intent?.action) {
            ACTION_FOO -> {
                val request: Request = Request.Builder().url(URL).build()
                okHttpClient.newCall(request).enqueue(object: Callback {
                    override fun onFailure(call: Call?, e: IOException?) {
                        Log.d("mytag", "Failed call: $e")
                    }

                    override fun onResponse(call: Call?, response: Response?) {
                        param1 = response?.body()?.string().toString()
                        Log.d("mytag", "from service: $param1")
                    }
                })
                Log.d("mytag", "param1: $param1")
                handleActionFoo(param1)
            }
        }
    }

    private fun handleActionFoo(param1: String?) {
        Log.d("mytag", "param1: $param1")
        if (param1 != null) {
            startActionFoo(this, param1)
        }
    }


    companion object {
        @JvmStatic
        fun startActionFoo(context: Context, param1: String) {
            val intent = Intent(context, QuotesIntentService::class.java).apply {
                action = ACTION_FOO
                putExtra(EXTRA_PARAM1, param1)
            }
            context.startService(intent)
        }
    }
}