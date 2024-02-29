package com.metel20.monitoringcurrency.core.modules

import com.metel20.data.core.ProvideResources
import com.metel20.data.load.BaseLoadCurrencyRepository
import com.metel20.data.load.cache.CurrencyCacheDataSource
import com.metel20.data.load.cloud.LoadCurrencyCloudDataSource
import com.metel20.domain.LoadCurrenciesRepository

interface ProvideInstance {

    fun provideLoadRepository(
        cacheDataSource: CurrencyCacheDataSource.Mutable,
        cloudDataSource: LoadCurrencyCloudDataSource,
        provideResources: ProvideResources
    ): LoadCurrenciesRepository

    class Base : ProvideInstance {
        override fun provideLoadRepository(
            cacheDataSource: CurrencyCacheDataSource.Mutable,
            cloudDataSource: LoadCurrencyCloudDataSource,
            provideResources: ProvideResources
        ) = BaseLoadCurrencyRepository(
            cloudDataSource,
            cacheDataSource,
            provideResources,
        )
    }
}
