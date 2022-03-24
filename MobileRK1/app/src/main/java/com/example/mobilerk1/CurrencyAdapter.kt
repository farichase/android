package com.example.mobilerk1

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


class CurrencyAdapter(private val fragment: Fragment)
        : RecyclerView.Adapter<CurrencyAdapter.ElemViewHolder>() {

    class ElemViewHolder(
            iv: View,
            private val fragment: Fragment)
        : RecyclerView.ViewHolder(iv) {

        private lateinit var holdedData: DataElement
        private val tvTime: TextView = iv.findViewById(R.id.tvCurrencyTime)
        private val tvHigh: TextView = iv.findViewById(R.id.tvCurrencyHigh)

        init {
            itemView.setOnClickListener {
                val bundle = bundleOf(DetailFragment.ELEM_KEY to holdedData)
                fragment.findNavController()
                    .navigate(R.id.action_mainFragment_to_detailFragment, bundle)
            }
        }

        fun setData(data: DataElement) {
            holdedData = data
            val time = LocalDateTime.ofInstant(Instant.ofEpochSecond(data.time), ZoneOffset.UTC)
            tvTime.text = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(time)
            tvHigh.text = fragment.getString(R.string.currency_list_elem, data.low, data.high)
        }

        companion object {
            fun from(parent: ViewGroup, fragment: Fragment): ElemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.currency_item, parent, false)
                return ElemViewHolder(view, fragment)
            }
        }
    }

    var data = listOf<DataElement>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElemViewHolder {
        return ElemViewHolder.from(parent, fragment)
    }

    override fun onBindViewHolder(holder: ElemViewHolder, position: Int) {
        holder.setData(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}