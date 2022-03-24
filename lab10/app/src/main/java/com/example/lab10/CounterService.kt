package com.example.lab10

import android.app.IntentService
import android.content.Intent

class CounterService: IntentService(CounterService::class.java.simpleName) {

    companion object {
        const val COUNT = "lab10.action.COUNT"
        var run = false
        var i = 0
    }
    override fun onHandleIntent(intent: Intent?) {
        run = true
        val sec = 1000L
        while (run) {
            val broadcast = Intent(COUNT)
            broadcast.putExtra("count", i)
            i++
            sendBroadcast(broadcast)
            Thread.sleep(sec * 1)
        }
    }
}