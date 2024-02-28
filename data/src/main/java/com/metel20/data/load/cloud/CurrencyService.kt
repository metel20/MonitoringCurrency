package com.metel20.data.load.cloud

import retrofit2.Call
import retrofit2.http.GET

interface CurrencyService {

    @GET("currencies")
    fun load(): Call<HashMap<String, String>>
}