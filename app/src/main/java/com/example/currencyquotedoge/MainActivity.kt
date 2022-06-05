package com.example.currencyquotedoge

import android.content.*
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException


class MainActivity : AppCompatActivity() {

    lateinit var textView: TextView
    lateinit var textUSD: TextView
    lateinit var textEUR: TextView
    lateinit var textRUB: TextView

    lateinit var timeReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val URL =
            "https://min-api.cryptocompare.com/data/price?fsym=DOGE&tsyms=USD,EUR,RUB&api_key=${R.string.api_key}"
        var firstJson = ""
        val request: Request = Request.Builder().url(URL).build()
        OkHttpClient().newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                Log.d("mytag", "Failed call: $e")
            }

            override fun onResponse(call: Call?, response: Response?) {
                firstJson = response?.body()?.string().toString()
                timeUpdate(firstJson)
            }
        })

        intent = Intent(this, QuotesIntentService::class.java)
        startService(intent)

        timeReceiver = TimeReceiver()
        registerReceiver(timeReceiver, IntentFilter("android.intent.action.TIME_TICK"))


        textView = findViewById(R.id.text_view)

        textUSD = findViewById(R.id.text_USD)
        textEUR = findViewById(R.id.text_EUR)
        textRUB = findViewById(R.id.text_RUB)

    }


    fun timeUpdate(json: String?) {
        Log.d("mytag", "from main: $json")
        val quotes: Quotes = Gson().fromJson(json, Quotes::class.java)
        var text = getString(R.string.for_text_view, "USD") + " " + quotes.USD.toString()
        textUSD.text = text
        text = getString(R.string.for_text_view, "EUR") + " " + quotes.EUR.toString()
        textEUR.text = text
        text = getString(R.string.for_text_view, "RUB") + " " + quotes.RUB.toString()
        textRUB.text = text
    }



}
