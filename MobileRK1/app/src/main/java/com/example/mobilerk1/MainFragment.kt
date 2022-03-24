package com.example.mobilerk1

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager

import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import com.example.mobilerk1.api.PriceConverter

class MainFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var currencyData = listOf<DataElement>()

    private val viewModel: PriceConverter by lazy {
        ViewModelProvider(this).get(PriceConverter::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    private fun hideKeyboard(tv: TextView) {
        val imm: InputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(tv.windowToken, 0)
    }

    private fun loadData(from1: String) {
        var from = from1
        val preference = context?.getSharedPreferences("com.example.mobilerk1_preferences", Context.MODE_PRIVATE)
        val to = preference?.getString("list_preference_1", "BTC").toString()
        var limit = preference?.getString("edit_text_preference_2", "10")?.toInt()
        if (limit == null)
            limit = 3
        if (from == "")
            from = "USD"
        viewModel.updateDataElements(from, to, (limit - 1))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tvEditCurrency = view.findViewById<AutoCompleteTextView>(R.id.editCurrency)

        // RecyclerView initialization
        viewManager = LinearLayoutManager(context)
        viewAdapter = CurrencyAdapter(this)
        recyclerView = view.findViewById<RecyclerView>(R.id.currencyList).apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }


        // Action on GO keyboard button pressed
        tvEditCurrency.setOnEditorActionListener(OnEditorActionListener { tv, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                hideKeyboard(tv)
                loadData(tvEditCurrency.text.toString())
                return@OnEditorActionListener true
            }
            false
        })

        // Action on LOAD button clicked
        view.findViewById<Button>(R.id.reRequestButton).setOnClickListener {
            hideKeyboard(tvEditCurrency)
            loadData(tvEditCurrency.text.toString())
        }

        // Action on external link click
        view.findViewById<TextView>(R.id.tvExtLink).setOnClickListener {
            val uri = Uri.parse("https://www.cryptocompare.com/")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        // Data loading into RecyclerView
        viewModel.getDataElements().observe(viewLifecycleOwner, { element ->
            run {
                currencyData = element ?: listOf()
                (viewAdapter as CurrencyAdapter).data = currencyData
            }
        })

        super.onViewCreated(view, savedInstanceState)
    }
}