package com.app.weatherapp.data

import com.app.weatherapp.data.model.product
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("data/2.5/weather")
    suspend fun getProductsList(
        @Query("q") cityName : String,
        @Query("appid") apiKey: String = "cd3ed3223437cde67c8f65378e9894c0",
        ): product

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/"
        //https://api.openweathermap.org/weather?q=toronto&appid=cd3ed3223437cde67c8f65378e9894c0
        //weather?q=toronto&appid=cd3ed3223437cde67c8f65378e9894c0
    }

}