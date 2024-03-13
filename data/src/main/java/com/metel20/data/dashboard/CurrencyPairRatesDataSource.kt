package com.metel20.data.dashboard

import com.metel20.dashboard.DashboardItem
import com.metel20.data.dashboard.cache.CurrencyPairCache

interface CurrencyPairRatesDataSource {

    suspend fun data(favoriteRates: List<CurrencyPairCache>): List<DashboardItem>

    class Base(
        private val currentTimeInMillis: CurrentTimeInMillis,
        private val updatedRateDataSource: UpdatedRateDataSource
    ) : CurrencyPairRatesDataSource {

        override suspend fun data(favoriteRates: List<CurrencyPairCache>) =
            favoriteRates.map { favoriteRate ->
                DashboardItem.Base(
                    from = favoriteRate.from,
                    to = favoriteRate.to,
                    rates = if (favoriteRate.isNotFresh(currentTimeInMillis))
                        updatedRateDataSource.updatedRate(favoriteRate)
                    else
                        favoriteRate.rate
                )
            }
    }
}