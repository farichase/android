package com.example.lab9

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView



class MainActivity : AppCompatActivity() {

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                startActivity(Intent(this, CalendarActivity::class.java))
            } else {
                Toast.makeText(this, "Permission required", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val recyclerView = findViewById<RecyclerView>(R.id.rows_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = UserAdapter(getRows())

        findViewById<Button>(R.id.add_user_button).setOnClickListener {
            val name = findViewById<EditText>(R.id.name_input).text.toString()
            val values = ContentValues()
            values.put(UserContentProvider.name, name)
            contentResolver.insert(UserContentProvider.CONTENT_URI, values)
            val recyclerView = findViewById<RecyclerView>(R.id.rows_list)
            recyclerView.adapter = UserAdapter(getRows())
        }

        findViewById<Button>(R.id.other_provider_button).setOnClickListener {
            when (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR)){
                PackageManager.PERMISSION_GRANTED -> {
                    startActivity(Intent(this, CalendarActivity::class.java))
                }
                else -> {
                    requestPermissionLauncher.launch(Manifest.permission.READ_CALENDAR)
                }
            }
        }
    }
    @SuppressLint("Range")
    private fun getRows():List<User> {
        val users = mutableListOf<User>()
        val cursor = contentResolver.query(UserContentProvider.CONTENT_URI, null, null, null, null)

        cursor?.use {
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getString(cursor.getColumnIndex("id"))
                    val name = cursor.getString(cursor.getColumnIndex("name"))
                    users.add(User(id, name))
                } while (cursor.moveToNext())
            } else {
                Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show()
            }
        }
        return users.toList()
    }
}