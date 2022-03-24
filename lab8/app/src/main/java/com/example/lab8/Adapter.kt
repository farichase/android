package com.example.lab8

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class RecyclerAdapter(val files: Array<File>?):
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var info: TextView? = null
        init {
            info = itemView.findViewById(R.id.info)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var activity = holder.itemView.context as MainActivity

        holder.info?.text = files?.get(position)?.name

        if (files?.get(position)?.isDirectory == true) {
            holder.info?.setBackgroundColor(Color.GRAY)
        }

        holder.itemView.setOnClickListener {
            var file = files?.get(position)
            if (file?.isDirectory == true) {
                MainActivity.currentDir = file
                activity.restart()
            } else {
                val intent = Intent(activity, EditorActivity::class.java)
                intent.data = Uri.fromFile(file)
                activity.startActivity(intent)
            }
        }
    }


    override fun getItemCount(): Int {
        if (files != null) {
            return files.size
        }
        return 0
    }
}

