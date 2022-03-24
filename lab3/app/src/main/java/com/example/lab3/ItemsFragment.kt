package com.example.lab3

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab3.databinding.FragmentMainBinding

class ItemsFragment: Fragment(R.layout.fragment_main) {

    private var binding: FragmentMainBinding? = null
    private var adapter: RecyclerViewAdapter? = null
    var callback: OnItemSelectedListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        adapter = RecyclerViewAdapter(callback)

        val layoutManager = LinearLayoutManager(context)
        binding.recycler.layoutManager = layoutManager
        binding.recycler.adapter = adapter
        return binding.root
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    companion object {

        fun newInstance(): ItemsFragment {
            return ItemsFragment()
        }
    }
}
