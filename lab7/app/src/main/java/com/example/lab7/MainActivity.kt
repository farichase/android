package com.example.lab7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var dbAdapter: ListAdapter
    private lateinit var db: ParserDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewManager = LinearLayoutManager(this)
        dbAdapter = ListAdapter()
        recyclerView = findViewById(R.id.recView)
        recyclerView.apply {
            layoutManager = viewManager
            adapter = dbAdapter
        }

        db = ParserDB.getInst(this)
        GlobalScope.launch {
            val data = db.tableDao().getAll()
            runOnUiThread { dbAdapter.data = data }
        }

        findViewById<Button>(R.id.addButton).setOnClickListener {
            val book = findViewById<EditText>(R.id.bookField)
            val author = findViewById<EditText>(R.id.authorField)
            GlobalScope.launch {
                db.tableDao().insert(Parser(0, book.text.toString(), author.text.toString()))
                val data = db.tableDao().getAll()
                runOnUiThread {
                    dbAdapter.data = data
                    book.setText("")
                    author.setText("")
                }
            }
        }

        ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    GlobalScope.launch {
                        db.tableDao().delete((viewHolder as ListAdapter.ElemViewHolder).id)
                        val data = db.tableDao().getAll()
                        runOnUiThread { dbAdapter.data = data }
                    }
                }

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }
            }
        ).attachToRecyclerView(recyclerView)
    }
}