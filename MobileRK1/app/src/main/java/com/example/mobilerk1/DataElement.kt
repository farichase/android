package com.example.mobilerk1
import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize
@Parcelize
data class DataElement(
    @Json(name = "time") val time: Long,
    @Json(name = "high") val high: Float,
    @Json(name = "low") val low: Float,
    @Json(name = "open") val open: Float,
    @Json(name = "volumefrom") val volumeFrom: Float,
    @Json(name = "volumeto") val volumeTo: Float,
    @Json(name = "close") val close: Float,
    @Json(name = "conversionType") val conversionType: String,
    @Json(name = "conversionSymbol") val conversionSymbol: String,
) : Parcelable