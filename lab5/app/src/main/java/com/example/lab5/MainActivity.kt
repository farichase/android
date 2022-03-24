package com.example.lab5

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private var books = listOf<Parse>()

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(KEY)) {
                val str = savedInstanceState.getString(KEY) as String
                findViewById<EditText>(R.id.onSavedVar).setText(str)
            }
        }

        viewManager = LinearLayoutManager(this)
        viewAdapter = ListAdapter()
        (viewAdapter as ListAdapter).data = books

        findViewById<RecyclerView>(R.id.recView).apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        val upd = findViewById<Button>(R.id.upd)
        upd.setOnClickListener {
            val data = RetrofitInterface.create().getData()
            data.enqueue(object : Callback<List<Parse>> {
                override fun onResponse(call: Call<List<Parse>>?,
                                        response: Response<List<Parse>>?) {
                    if (response?.body() != null) {
                        books = response.body()!!
                        (viewAdapter as ListAdapter).data = books
                    }
                }

                override fun onFailure(call: Call<List<Parse>>?, t: Throwable?) {
                    Log.e("MOSHI", "Failure")
                    Log.e("MOSHI", t.toString())
                }
            })
        }
    }



    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val onStStr = findViewById<EditText>(R.id.onSavedVar)
        outState.putString(KEY, onStStr.text.toString())
    }

    override fun onPause() {
        Log.i("LIFECYCLE", "onPause")
        super.onPause()
    }

    override fun onStop() {
        val onstp = findViewById<EditText>(R.id.staticVar)
        staticVar = onstp.text.toString()
        super.onStop()
    }

    override fun onDestroy() {
        Log.i("LIFECYCLE", "onDestroy")
        super.onDestroy()
    }

    override fun onResume() {
        Log.i("LIFECYCLE", "onResume")
        super.onResume()
    }

    override fun onRestart() {
        Log.i("LIFECYCLE", "onRestart")
        super.onRestart()
    }
    companion object{
        private const val KEY = "Key"
        var staticVar : String = "Static"
    }
}