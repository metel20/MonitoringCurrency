package com.metel20.data.dashboard

import com.metel20.dashboard.DashboardRepository
import com.metel20.dashboard.DashboardResult
import com.metel20.data.core.HandleError
import com.metel20.data.dashboard.cache.CurrencyPairCacheDataSource

class BaseDashboardRepository(
    private val cacheDataSource: CurrencyPairCacheDataSource.Read,
    private val currencyPairRatesDataSource: CurrencyPairRatesDataSource,
    private val handleError: HandleError
) : DashboardRepository {

    override suspend fun dashboardItems(): DashboardResult {
        val favoritePairs = cacheDataSource.read()
        return if (favoritePairs.isEmpty())
            DashboardResult.Empty
        else try {
            DashboardResult.Success(currencyPairRatesDataSource.data(favoritePairs))
        } catch (e: Exception) {
            DashboardResult.Error(handleError.handleError(e))
        }
    }
}