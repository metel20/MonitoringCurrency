package com.metel20.monitoringcurrency.core.modules

import com.metel20.data.core.HandleError
import com.metel20.data.load.BaseLoadCurrencyRepository
import com.metel20.data.load.cache.CurrencyCacheDataSource
import com.metel20.data.load.cloud.LoadCurrencyCloudDataSource
import com.metel20.monitoringcurrency.core.Core
import com.metel20.presentation.core.Clear
import com.metel20.presentation.loading.LoadUiObservable
import com.metel20.presentation.loading.LoadViewModel

class LoadModule(private val core: Core, private val clear: Clear) :
    Module<LoadViewModel> {

    override fun viewModel() = LoadViewModel(
        repository = BaseLoadCurrencyRepository(
            cacheDataSource = CurrencyCacheDataSource.Base(core.provideDatabase().currenciesDao()),
            cloudDataSource = LoadCurrencyCloudDataSource.Base(
                retrofit = core.provideRetrofit()
            ),
            handleError = HandleError.Base(provideResources = core.provideResources())
        ),
        uiObservable = LoadUiObservable.Base(),
        navigation = core.provideNavigation(),
        clear = clear,
        runAsync = core.provideRunAsync()
    )
}