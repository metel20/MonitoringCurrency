package com.metel20.data.load.cloud

import retrofit2.Retrofit

interface LoadCurrencyCloudDataSource {
    suspend fun currencies(): HashMap<String, String>

    class Base(
        private val service: CurrencyService,
    ) : LoadCurrencyCloudDataSource {

        constructor(retrofit: Retrofit) : this(retrofit.create(CurrencyService::class.java))

        override suspend fun currencies(): HashMap<String, String> {
            return service.load().execute().body()!!
        }
    }
}