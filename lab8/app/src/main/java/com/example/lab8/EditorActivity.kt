package com.example.lab8

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class EditorActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)

        val uri = intent.data
        val file = File(uri?.path)
        val editor = findViewById<EditText>(R.id.editor)
        editor.setText(file.readText())

        findViewById<Button>(R.id.cancel_button).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.save_button).setOnClickListener {
            val newFileText = editor.text.toString()
            file.outputStream().write(newFileText.toByteArray())
            finish()
        }
    }
}