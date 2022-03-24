package com.example.lab3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity(), OnItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = supportFragmentManager.findFragmentById(R.id.container)
        if (fragment == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, ItemsFragment.newInstance())
                .commit()
        }
    }

    override fun onAttachFragment(fragment: Fragment) {
        if (fragment is ItemsFragment) {
            fragment.callback = this
        }
    }

    override fun onItemSelected(name: String) {
        Toast.makeText(applicationContext, name, Toast.LENGTH_SHORT).show()
    }

}