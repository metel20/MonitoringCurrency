package com.metel20.monitoringcurrency.core.modules

import com.metel20.data.core.HandleError
import com.metel20.data.dashboard.BaseDashboardRepository
import com.metel20.data.dashboard.CurrencyPairRatesDataSource
import com.metel20.data.dashboard.CurrentTimeInMillis
import com.metel20.data.dashboard.UpdatedRateDataSource
import com.metel20.data.dashboard.cache.CurrencyPairCacheDataSource
import com.metel20.data.dashboard.cloud.LatestCurrencyCloudDataSource
import com.metel20.monitoringcurrency.core.Core
import com.metel20.presentation.core.Clear
import com.metel20.presentation.dashboard.DashboardUiObservable
import com.metel20.presentation.dashboard.DashboardViewModel

class DashboardModule(
    private val core: Core,
    private val clear: Clear
) : Module<DashboardViewModel> {

    override fun viewModel(): DashboardViewModel {
        val cacheDataSource =
            CurrencyPairCacheDataSource.Base(core.provideDatabase().latestCurrencyDao())
        val currentTimeInMillis = CurrentTimeInMillis.Base()

        return DashboardViewModel(
            navigation = core.provideNavigation(),
            observable = DashboardUiObservable.Base(),
            repository = BaseDashboardRepository(
                cacheDataSource = cacheDataSource,
                currencyPairRatesDataSource = CurrencyPairRatesDataSource.Base(
                    currentTimeInMillis = currentTimeInMillis,
                    updatedRateDataSource = UpdatedRateDataSource.Base(
                        currentTimeInMillis = currentTimeInMillis,
                        cloudDataSource = LatestCurrencyCloudDataSource.Base(
                            retrofit = core.provideRetrofit()
                        ),
                        cacheDataSource = cacheDataSource
                    )
                ),
                handleError = HandleError.Base(core.provideResources())
            ),
            clear = clear,
            runAsync = core.provideRunAsync()
        )
    }
}