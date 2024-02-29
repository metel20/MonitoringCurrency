package com.metel20.monitoringcurrency.core

import android.content.Context
import androidx.room.Room
import com.metel20.data.core.ProvideResources
import com.metel20.data.load.cache.CurrencyDataBase
import com.metel20.presentation.core.Navigation
import com.metel20.presentation.core.RunAsync

interface Core {

    fun provideNavigation(): Navigation

    fun provideRunAsync(): RunAsync

    fun provideResources(): ProvideResources

    fun provideCurrencyDataBase(): CurrencyDataBase


    class Base(private val context: Context) : Core {

        private val navigation by lazy { Navigation.Base() }
        private val provideResources by lazy { BaseProvideResources(context = context) }
        private val runAsync by lazy { RunAsync.Base() }
        private val dataBase by lazy {
            Room.databaseBuilder(
                context,
                CurrencyDataBase::class.java,
                "currencyDataBase"
            ).build()
        }

        override fun provideNavigation() = navigation
        override fun provideRunAsync() = runAsync
        override fun provideResources() = provideResources
        override fun provideCurrencyDataBase(): CurrencyDataBase = dataBase
    }
}