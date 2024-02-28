package com.metel20.data.load.cloud

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface LoadCurrencyCloudDataSource {
    suspend fun currencies(): HashMap<String, String>

    class Base(
        private val service: CurrencyService,
    ) : LoadCurrencyCloudDataSource {

        constructor() : this(
            Retrofit.Builder().baseUrl("https://api.frankfurter.app/")
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(CurrencyService::class.java)
        )

        override suspend fun currencies(): HashMap<String, String> {
            return service.load().execute().body()!!
        }
    }
}