package com.example.lab2

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.example.lab2.databinding.ActivityMainBinding
import java.util.*




class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId: Int = item.itemId;
        if (itemId == R.id.action_refresh) {
            recreate();
            return true;
        } else if (itemId == R.id.action_change_lang) {
            if (resources.configuration.locale.displayLanguage.equals("английский")) {
                resources.configuration.setLocale(Locale("ru"));
                resources.updateConfiguration(resources.configuration, null);
                recreate();
            } else {
                resources.configuration.setLocale(Locale("en"));
                resources.updateConfiguration(resources.configuration, null);
                recreate();
            }
        }
        return super.onOptionsItemSelected(item)
    }


    fun buttonCallback(view: android.view.View) {
        binding.button.setText(R.string.clicked_button);
    }
}