package com.metel20.data.load

import com.metel20.data.core.ProvideResources
import com.metel20.data.load.cache.CurrencyCache
import com.metel20.data.load.cache.CurrencyCacheDataSource
import com.metel20.data.load.cloud.LoadCurrencyCloudDataSource
import com.metel20.domain.LoadCurrenciesRepository
import com.metel20.domain.LoadCurrenciesResult
import java.net.UnknownHostException

class BaseLoadCurrencyRepository(
    private val cloudDataSource: LoadCurrencyCloudDataSource,
    private val cacheDataSource: CurrencyCacheDataSource.Mutable,
    private val provideResources: ProvideResources
) : LoadCurrenciesRepository {

    override suspend fun loadCurrencies(): LoadCurrenciesResult {
        return try {
            if (cacheDataSource.read().isEmpty()) {
                cacheDataSource.save(cloudDataSource.currencies().map {
                    CurrencyCache(it.key, it.value)
                })
            }
            LoadCurrenciesResult.Success
        } catch (e: Exception) {
            if (e is UnknownHostException)
                LoadCurrenciesResult.Error(provideResources.noInternetConnectionMessage())
            else
                LoadCurrenciesResult.Error(provideResources.serviceUnavailableMessage())
        }
    }
}
