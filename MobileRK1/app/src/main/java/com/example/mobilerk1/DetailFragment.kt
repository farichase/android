package com.example.mobilerk1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class DetailFragment : Fragment() {
    companion object {
        const val ELEM_KEY = "elem"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    private fun formatTime(epoch: Long?): String {
        if (epoch == null)
            return ""
        val time = LocalDateTime.ofInstant(Instant.ofEpochSecond(epoch), ZoneOffset.UTC)
        return DateTimeFormatter.ofPattern("dd.MM.yyyy").format(time)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<TextView>(R.id.tvDetailDate).text = getString(R.string.details_date,
            formatTime(arguments?.getParcelable<DataElement>(ELEM_KEY)?.time))

        view.findViewById<TextView>(R.id.tvDetailHigh).text = getString(R.string.details_high,
            arguments?.getParcelable<DataElement>(ELEM_KEY)?.high)
        view.findViewById<TextView>(R.id.tvDetailLove).text = getString(R.string.details_love,
            arguments?.getParcelable<DataElement>(ELEM_KEY)?.low)
        view.findViewById<TextView>(R.id.tvDetailOpen).text = getString(R.string.details_open,
            arguments?.getParcelable<DataElement>(ELEM_KEY)?.open)
        view.findViewById<TextView>(R.id.tvDetailVolto).text = getString(R.string.details_volt,
            arguments?.getParcelable<DataElement>(ELEM_KEY)?.volumeTo)
        view.findViewById<TextView>(R.id.tvDetailVolfrom).text = getString(R.string.details_wolf,
            arguments?.getParcelable<DataElement>(ELEM_KEY)?.volumeFrom)
        view.findViewById<TextView>(R.id.tvDetailClose).text = getString(R.string.details_close,
            arguments?.getParcelable<DataElement>(ELEM_KEY)?.close)

        view.findViewById<TextView>(R.id.tvDetailConvType).text =
            getString(R.string.details_conv_type,
            arguments?.getParcelable<DataElement>(ELEM_KEY)?.conversionType)
        view.findViewById<TextView>(R.id.tvDetailConvSymb).text =
            getString(R.string.details_conv_symb,
            arguments?.getParcelable<DataElement>(ELEM_KEY)?.conversionSymbol)

        super.onViewCreated(view, savedInstanceState)
    }
}