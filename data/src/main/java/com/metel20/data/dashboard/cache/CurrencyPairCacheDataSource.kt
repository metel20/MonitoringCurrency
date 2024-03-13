package com.metel20.data.dashboard.cache

interface CurrencyPairCacheDataSource {

    interface Save {
        suspend fun save(currency: CurrencyPairCache)
    }

    interface Read {
        suspend fun read(): List<CurrencyPairCache>
    }

    interface Mutable : Save, Read

    class Base(private val latestCurrencyDao: LatestCurrencyDao) : Mutable {

        override suspend fun save(currency: CurrencyPairCache) {
            latestCurrencyDao.insert(currency)
        }

        override suspend fun read() = latestCurrencyDao.favoriteRates()
    }
}