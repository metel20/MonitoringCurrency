package com.metel20.data

import com.metel20.data.dashboard.cache.CurrencyPairCache
import com.metel20.data.dashboard.cache.CurrencyPairCacheDataSource
import com.metel20.data.load.cache.CurrencyCacheDataSource
import com.metel20.domain.SettingsRepository

class BaseSettingsRepository(
    private val allItemsCacheDataSource: CurrencyCacheDataSource.Read,
    private val favoriteCacheDataSource: CurrencyPairCacheDataSource.Mutable
) : SettingsRepository {

    override suspend fun getAllCurrencies(): List<String> {
        return allItemsCacheDataSource.read().map { it.name }
    }

    override suspend fun getAvailableDestinations(from: String): List<String> {
        val allCurrencies = allItemsCacheDataSource.read().map { it.name }.toMutableList()
        val favoriteCurrencies =
            favoriteCacheDataSource.read().filter { it.from == from }.map { it.to }
                .toMutableList()
        allCurrencies.removeAll(favoriteCurrencies)
        allCurrencies.remove(from)
        return allCurrencies

    }

    override suspend fun save(from: String, to: String) {
        favoriteCacheDataSource.save(CurrencyPairCache(from = from, to = to))
    }
}