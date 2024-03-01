package com.metel20.data.latestCurrency

import com.metel20.dashboard.DashboardRepository
import com.metel20.dashboard.DashboardResult
import com.metel20.data.core.HandleError
import com.metel20.data.latestCurrency.cache.LatestCurrencyCacheDataSource

class BaseDashboardRepository(
    private val cacheDataSource: LatestCurrencyCacheDataSource.Read,
    private val currencyPairRatesDataSource: CurrencyPairRatesDataSource,
    private val handleError: HandleError
) : DashboardRepository {

    override suspend fun dashboardItems(): DashboardResult {
        val favoriteRates = cacheDataSource.read()
        return if (favoriteRates.isEmpty())
            DashboardResult.Empty
        else try {
            DashboardResult.Success(currencyPairRatesDataSource.data(favoriteRates))
        } catch (e: Exception) {
            DashboardResult.Error(handleError.handleError(e))
        }
    }
}