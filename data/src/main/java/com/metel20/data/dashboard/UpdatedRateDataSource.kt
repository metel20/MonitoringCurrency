package com.metel20.data.dashboard

import com.metel20.data.dashboard.cache.CurrencyPairCache
import com.metel20.data.dashboard.cache.CurrencyPairCacheDataSource
import com.metel20.data.dashboard.cloud.LatestCurrencyCloudDataSource

interface UpdatedRateDataSource {
    suspend fun updatedRate(currentPair: CurrencyPairCache): Double

    class Base(
        private val currentTimeInMillis: CurrentTimeInMillis,
        private val cloudDataSource: LatestCurrencyCloudDataSource,
        private val cacheDataSource: CurrencyPairCacheDataSource.Save
    ) : UpdatedRateDataSource {

        override suspend fun updatedRate(currentPair: CurrencyPairCache): Double {
            val updatedRate = cloudDataSource.latestCurrency(currentPair.from, currentPair.to)
            cacheDataSource.save(
                CurrencyPairCache(
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
