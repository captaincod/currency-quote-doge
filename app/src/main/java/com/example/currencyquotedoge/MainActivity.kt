package com.example.currencyquotedoge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.gson.Gson
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import android.content.Intent

import android.content.BroadcastReceiver
import android.content.Context

import android.content.IntentFilter




class MainActivity : AppCompatActivity() {

    lateinit var textView: TextView
    lateinit var textUSD: TextView
    lateinit var textEUR: TextView
    lateinit var textRUB: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        textView = findViewById(R.id.text_view)
        textUSD = findViewById(R.id.text_USD)
        textEUR = findViewById(R.id.text_EUR)
        textRUB = findViewById(R.id.text_RUB)

        val URL =
            "https://min-api.cryptocompare.com/data/price?fsym=DOGE&tsyms=USD,EUR,RUB&api_key=${R.string.api_key}"
        val okHttpClient = OkHttpClient()

        val request: Request = Request.Builder().url(URL).build()
        okHttpClient.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                Log.d("mytag", "Failed call: $e")
            }

            override fun onResponse(call: Call?, response: Response?) {
                val json = response?.body()?.string()
                val quotes: Quotes = Gson().fromJson(json, Quotes::class.java)
                Log.d("mytag", quotes.toString())
                runOnUiThread {
                    textView.text = json.toString()
                    var text = getString(R.string.for_text_view, "USD") + " " + quotes.USD.toString()
                    textUSD.text = text
                    text = getString(R.string.for_text_view, "EUR") + " " + quotes.EUR.toString()
                    textEUR.text = text
                    text = getString(R.string.for_text_view, "RUB") + " " + quotes.RUB.toString()
                    textRUB.text = text
                }
            }
        })



    }


}
