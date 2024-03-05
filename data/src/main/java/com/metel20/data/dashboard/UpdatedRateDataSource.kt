package com.metel20.data.dashboard

import com.metel20.data.dashboard.cache.LatestCurrencyCache
import com.metel20.data.dashboard.cache.LatestCurrencyCacheDataSource
import com.metel20.data.dashboard.cloud.LatestCurrencyCloudDataSource

interface UpdatedRateDataSource {
    suspend fun updatedRate(currentPair: LatestCurrencyCache): Double

    class Base(
        private val currentTimeInMillis: CurrentTimeInMillis,
        private val cloudDataSource: LatestCurrencyCloudDataSource,
        private val cacheDataSource: LatestCurrencyCacheDataSource.Save
    ) : UpdatedRateDataSource {

        override suspend fun updatedRate(currentPair: LatestCurrencyCache): Double {
            val updatedRate = cloudDataSource.latestCurrency(currentPair.from, currentPair.to)
            cacheDataSource.save(
                LatestCurrencyCache(
                    currentPair.from,
                    currentPair.to,
                    updatedRate,
                    currentTimeInMillis.time()
                )
            )
            return updatedRate
        }
    }
}
