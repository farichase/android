package com.example.lab6

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.preference.PreferenceManager

class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val preference = PreferenceManager.getDefaultSharedPreferences(this)
        val isDark = preference.getString("theme", "") == "dark"
        if (isDark) {
            setTheme(android.R.style.Theme_Black)
        } else {
            setTheme(android.R.style.Theme_Light)
        }
        setContentView(R.layout.activity_main)
        PreferenceManager.getDefaultSharedPreferences(this)
            .registerOnSharedPreferenceChangeListener(this)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.settings) {
            startActivity(Intent(this, SettingsActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        PreferenceManager.getDefaultSharedPreferences(this)
            .unregisterOnSharedPreferenceChangeListener(this)
        super.onDestroy()
    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        val preference = PreferenceManager.getDefaultSharedPreferences(this)
        val nRevStr = findViewById<TextView>(R.id.textView)
        nRevStr.text = preference.getString("number", "")
        if (p1 == "theme") {
            recreate()
        }
        if (preference.getBoolean("rev", true)){
            nRevStr.text = nRevStr.text.reversed()
        } else {
            nRevStr.text = nRevStr.text
        }
        if (preference.getBoolean("pict", true)){
            findViewById<ImageView>(R.id.img).visibility = View.VISIBLE
        } else {
            findViewById<ImageView>(R.id.img).visibility = View.INVISIBLE
        }
    }
}