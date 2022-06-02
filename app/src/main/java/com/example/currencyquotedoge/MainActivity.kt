package com.example.currencyquotedoge

import android.content.*
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {

    lateinit var textView: TextView
    lateinit var textUSD: TextView
    lateinit var textEUR: TextView
    lateinit var textRUB: TextView

    lateinit var timeReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        intent = Intent(this, QuotesService::class.java)
        startService(intent)

        timeReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                (context as MainActivity).timeUpdate()
            }
        }
        registerReceiver(timeReceiver, IntentFilter("android.intent.action.TIME_TICK"))


        textView = findViewById(R.id.text_view)

        textUSD = findViewById(R.id.text_USD)
        textEUR = findViewById(R.id.text_EUR)
        textRUB = findViewById(R.id.text_RUB)

    }

    fun timeUpdate() {
        val json = intent.getStringExtra("json")
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
