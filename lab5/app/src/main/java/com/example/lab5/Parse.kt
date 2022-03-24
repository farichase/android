package com.example.lab5

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

import retrofit2.http.GET

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import com.squareup.moshi.Json

data class Parse (
    @field:Json(name = "book") var book: String,
    @field:Json(name = "author") var author: String
) : Parcelable {
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p: Parcel, flags: Int) {
        p.writeString(book)
        p.writeString(author)
    }

    constructor(p: Parcel) : this("", "") {
        book = p.readString().toString()
        author = p.readString().toString()
    }

    companion object CREATOR : Creator<Parse> {
        override fun createFromParcel(parcel: Parcel): Parse {
            return Parse(parcel)
        }

        override fun newArray(size: Int): Array<Parse?> {
            return arrayOfNulls(size)
        }
    }
}

interface RetrofitInterface {
    @GET("EW6bhigQ")
    fun getData() : Call<List<Parse>>

    companion object {
        var BASE_URL = "https://pastebin.com/raw/"

        fun create() : RetrofitInterface {
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
            return retrofit.create(RetrofitInterface::class.java)

        }
    }
}
