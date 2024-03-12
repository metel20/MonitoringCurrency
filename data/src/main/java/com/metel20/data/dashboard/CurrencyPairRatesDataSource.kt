package com.metel20.data.dashboard

import com.metel20.dashboard.DashboardItem
import com.metel20.data.dashboard.cache.LatestCurrencyCache

interface CurrencyPairRatesDataSource {

    suspend fun data(favoriteRates: List<LatestCurrencyCache>): List<DashboardItem>

    class Base(
        private val currentTimeInMillis: CurrentTimeInMillis,
        private val updatedRateDataSource: UpdatedRateDataSource
    ) : CurrencyPairRatesDataSource {

        override suspend fun data(favoriteRates: List<LatestCurrencyCache>) =
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