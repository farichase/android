package com.example.lab4

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Frag1 : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var resLauncher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        resLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { res ->
                if (res.resultCode == Activity.RESULT_OK) {
                    val str = res.data!!.getStringExtra(Input.RESULT_STRING_KEY)
                    requireView().findViewById<TextView>(R.id.getter).text = str
                }
            }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_frag1, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btn_alarm).setOnClickListener {
            val intent = Intent(AlarmClock.ACTION_SHOW_ALARMS)
            startActivity(intent)
        }

        view.findViewById<Button>(R.id.btn_browser).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://yandex.ru")
            startActivity(intent)
        }
        view.findViewById<Button>(R.id.btn_contact).setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = ContactsContract.Contacts.CONTENT_TYPE
            startActivity(intent)
        }
        view.findViewById<Button>(R.id.btn_text).setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Hello, friend")
            startActivity(Intent.createChooser(shareIntent, "MyTitle"))
        }
        view.findViewById<Button>(R.id.button7).setOnClickListener {
            val intent = Intent(context, Input::class.java).apply {
                val sendText = view.findViewById<TextView>(R.id.getter).text.toString()
                putExtra(Input.DEFAULT_VAL_KEY, sendText)
            }
            resLauncher.launch(intent)
        }

    }

}