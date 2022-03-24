package com.example.mobilerk1.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilerk1.DataElement
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


data class HisToDayResponse(
    @Json(name = "Response") val response: String,
    @Json(name = "Message") val message: String,
    @Json(name = "HasWarning") val hasWarning: Boolean?,
    @Json(name = "Data") val data: HisToDayData?
)

data class HisToDayData(
    @Json(name = "Aggregated") val aggregated: Boolean,
    @Json(name = "TimeFrom") val timeFrom: Long,
    @Json(name = "TimeTo") val timeTo: Long,
    @Json(name = "Data") val data: List<DataElement>,
)


interface PriceConverterApiService {
    @GET("histoday")
    suspend fun getHisToDay(
        @Query("fsym") fsym: String,
        @Query("tsym") tsym: String,
        @Query("limit") limit: Int
    ): HisToDayResponse
}
private const val BASE_URL = "https://min-api.cryptocompare.com/data/v2/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

internal val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

object PriceConverterApi {
    private val priceConverterService: PriceConverterApiService by lazy {
        retrofit.create(PriceConverterApiService::class.java)
    }

    suspend fun getHistoryToDay(from: String, to: String, limit: Int): HisToDayResponse {
        return priceConverterService.getHisToDay(from, to, limit);
    }
}
class PriceConverter : ViewModel() {
    private val dataElements = MutableLiveData<List<DataElement>?>()

    fun updateDataElements(from: String, to: String, limit: Int) {
        viewModelScope.launch {
            try {
                val response = PriceConverterApi.getHistoryToDay(from, to, limit);

                if (response.data == null) {
                    throw Exception(response.message)
                }

                dataElements.value = response.data.data
            } catch (e: Exception) {
                Log.e(PriceConverter::class.simpleName, e.toString())
                dataElements.value = null
            }
        }
    }

    fun getDataElements(): LiveData<List<DataElement>?> {
        return dataElements;
    }
}