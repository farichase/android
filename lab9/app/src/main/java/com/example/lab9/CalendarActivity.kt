package com.example.lab9

import android.os.Bundle
import android.provider.CalendarContract
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView



class CalendarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        val recyclerView = findViewById<RecyclerView>(R.id.contacts_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = StringListAdapter(getContacts())
    }

    private fun getContacts(): List<String> {
        var calendar_events = mutableListOf<String>()
        var cursor = contentResolver.query(CalendarContract.Events.CONTENT_URI, null, null, null, null)
        cursor?.use {
            cursor.moveToFirst()
            do {
                val ind = it.getColumnIndex(CalendarContract.Events.TITLE)
                val name = it.getString(ind)
                calendar_events.add(name)
            }while (it.moveToNext())
        }
        return calendar_events.toList()
    }


}