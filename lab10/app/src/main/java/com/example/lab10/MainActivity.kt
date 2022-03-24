package com.example.lab10

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var myReceiver: Receiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Toast.makeText(context, "Time changed!", Toast.LENGTH_SHORT).show()
            }
        }, IntentFilter("android.intent.action.TIME_TICK"))

        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Toast.makeText(context, "Hello!", Toast.LENGTH_SHORT).show()
            }
        }, IntentFilter("android.intent.action.HELLO"))

        findViewById<Button>(R.id.hello_button).setOnClickListener {
            sendBroadcast(Intent("android.intent.action.HELLO"))
        }

        val filter = IntentFilter()
        filter.addAction(CounterService.COUNT)
        myReceiver = Receiver()
        registerReceiver(myReceiver, filter)


        findViewById<Button>(R.id.start_button).setOnClickListener {
            if (!CounterService.run) {
                var counterIntent = Intent(this, CounterService::class.java)
                startService(counterIntent)
            }
        }
        findViewById<Button>(R.id.stop_button).setOnClickListener {
            CounterService.run = false
        }
    }

    override fun onPause() {
        unregisterReceiver(myReceiver)
        super.onPause()
    }

}