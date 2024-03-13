package com.metel20.data.dashboard.cloud

import retrofit2.Retrofit

interface LatestCurrencyCloudDataSource {

    fun latestCurrency(from: String, to: String): Double

    class Base(private val currencyRateService: CurrencyRateService) :
        LatestCurrencyCloudDataSource {

        constructor(retrofit: Retrofit) : this(
            retrofit.create(CurrencyRateService::class.java)
        )

        override fun latestCurrency(from: String, to: String) =
            currencyRateService.latestCurrency(from, to).execute().body()!!.rate(to)
    }
}