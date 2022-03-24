package com.example.lab7

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListAdapter : RecyclerView.Adapter<ListAdapter.ElemViewHolder>() {
    class ElemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var id: Int = -1
        private val book: TextView = itemView.findViewById(R.id.book)
        private val author: TextView = itemView.findViewById(R.id.author)

        fun setData(msg: Parser) {
            id = msg.id
            book.text = msg.book
            author.text = msg.author
        }

        companion object {
            fun from(parent: ViewGroup) : ElemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.list_item, parent,false)
                return ElemViewHolder(view)
            }
        }

    }

    var data = listOf<Parser>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.ElemViewHolder {
        return ElemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ListAdapter.ElemViewHolder, position: Int) {
        holder.setData(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}