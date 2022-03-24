package com.example.lab3
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(
    private val callback: OnItemSelectedListener?,
): RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder>() {
    private val data: ArrayList<Data> = arrayListOf()

    init {
        data.add(Data("Maria"))
        data.add(Data("Ann"))
        data.add(Data("Tom"))
        data.add(Data("Kate"))
        data.add(Data("Elena"))
        data.add(Data("Ian"))
        data.add(Data("Simon"))
        data.add(Data("Alexandr"))
        data.add(Data("Rachel"))
        data.add(Data("Monica"))
        data.add(Data("Ross"))
        data.add(Data("Otis"))
        data.add(Data("Mave"))
        data.add(Data("Ella"))
        data.add(Data("Elza"))
    }
    class Data (
        val name: String,
    )
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item, parent, false)
        return ItemViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindItem(data[position])
    }

    override fun getItemCount(): Int = data.size

    class ItemViewHolder(
        itemView: View,
        callback: OnItemSelectedListener?
    ): RecyclerView.ViewHolder(itemView) {
        private val itemTextView: TextView = itemView.findViewById(R.id.number)

        fun bindItem(item: Data) {
            itemTextView.text = item.name
        }

        init {
            itemTextView.setOnClickListener {
                callback?.onItemSelected(
                    itemTextView.text.toString(),
                )
            }
        }
    }
}


