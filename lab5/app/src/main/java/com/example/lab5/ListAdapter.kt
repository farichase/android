package com.example.lab5

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListAdapter : RecyclerView.Adapter<ListAdapter.ElemViewHolder>() {
    class ElemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val bookId: TextView = itemView.findViewById(R.id.book)
        private val authorId: TextView = itemView.findViewById(R.id.author)
        private val cI = gI++


        fun setData(data: Parse) {
            bookId.text = data.book
            authorId.text = data.author
        }

        companion object{
            var gI = 0
            fun from(parent: ViewGroup): ElemViewHolder {
                val layoutInf = LayoutInflater.from(parent.context)
                val view = layoutInf.inflate(R.layout.list_item, parent, false)
                return ElemViewHolder(view)
            }
        }
    }

    var data = listOf<Parse>()
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