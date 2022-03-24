package com.example.lab4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class Input : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)
        val defaultVal = intent.getStringExtra(DEFAULT_VAL_KEY) ?: ""
        findViewById<EditText>(R.id.input).setText(defaultVal)

        findViewById<Button>(R.id.button9).setOnClickListener {
            val resIntent = Intent().apply {
                putExtra(RESULT_STRING_KEY, findViewById<EditText>(R.id.input).text.toString())
            }
            setResult(RESULT_OK, resIntent);
            finish()
        }
    }

    companion object {
        const val DEFAULT_VAL_KEY = "default_val"
        const val RESULT_STRING_KEY = "string_result"
    }
}
