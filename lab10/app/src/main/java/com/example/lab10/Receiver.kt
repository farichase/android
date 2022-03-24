package com.example.lab10

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.TextView
import android.widget.Toast

class Receiver: BroadcastReceiver() {
    @SuppressLint("SetTextI18n")
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            CounterService.COUNT -> {
                val activity = context as MainActivity
                activity.findViewById<TextView>(R.id.output_text).text = intent.extras?.getInt("count").toString()
            }
            else -> {}
        }
    }

}