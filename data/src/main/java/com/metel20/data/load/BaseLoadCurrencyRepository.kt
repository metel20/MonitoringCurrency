package com.metel20.data.load

import com.metel20.data.core.HandleError
import com.metel20.data.load.cache.CurrencyCacheDataSource
import com.metel20.data.load.cloud.LoadCurrencyCloudDataSource
import com.metel20.domain.LoadCurrenciesRepository
import com.metel20.domain.LoadCurrenciesResult

class BaseLoadCurrencyRepository(
    private val cloudDataSource: LoadCurrencyCloudDataSource,
    private val cacheDataSource: CurrencyCacheDataSource.Mutable,
    private val handleError: HandleError
) : LoadCurrenciesRepository {

    override suspend fun loadCurrencies(): LoadCurrenciesResult {
        return try {
            if (cacheDataSource.read().isEmpty()) {
                cacheDataSource.save(cloudDataSource.currencies())
            }
            LoadCurrenciesResult.Success
        } catch (e: Exception) {
            LoadCurrenciesResult.Error(handleError.handleError(e))
        }
    }
}
