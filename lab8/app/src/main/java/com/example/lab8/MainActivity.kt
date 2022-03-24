package com.example.lab8

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import android.widget.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    companion object {
        var currentDir: File? = null
    }
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (currentDir == null) {
            currentDir = this.filesDir
        }
        val recyclerView = findViewById<RecyclerView>(R.id.rv_items)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecyclerAdapter(currentDir?.listFiles())

        val pathHint = findViewById<TextView>(R.id.path_hint)
        pathHint.text = "/${currentDir?.relativeTo(this.filesDir).toString()}"

        findViewById<Button>(R.id.back_button).isEnabled = currentDir != this.filesDir
        findViewById<Button>(R.id.back_button).setOnClickListener {
            currentDir = currentDir?.parentFile
            restart()
        }
        findViewById<Button>(R.id.new_dir_button).setOnClickListener {
            createDir()
        }

        findViewById<Button>(R.id.new_file_button).setOnClickListener {
            createFile()
        }
    }


    fun restart() {
        finish()
        startActivity(intent)
    }

    private fun createDir() {
    val name = findViewById<EditText>(R.id.dirname_input).text.toString()

    var newDir = File(currentDir, name)
        if (!newDir.exists()) {
            try {
                newDir.mkdir()
                val recyclerView = findViewById<RecyclerView>(R.id.rv_items)
                recyclerView.adapter = RecyclerAdapter(currentDir?.listFiles())
            } catch (e : IOException){
                Toast.makeText(this, "Directory creation failed", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Directory already exists", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createFile() {
        val name = findViewById<EditText>(R.id.filename_input).text.toString()

        var newFile = File(currentDir, name)
        if (!newFile.exists()) {
            try {
               newFile.createNewFile()
               val recyclerView = findViewById<RecyclerView>(R.id.rv_items)
               recyclerView.adapter = RecyclerAdapter(currentDir?.listFiles())
            } catch (e : IOException){
                Toast.makeText(this, "File creation failed", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "File already exists", Toast.LENGTH_SHORT).show()
        }
    }

}