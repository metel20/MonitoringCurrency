package com.metel20.data.core

import androidx.room.Database
import androidx.room.RoomDatabase
import com.metel20.data.dashboard.cache.CurrencyPairCache
import com.metel20.data.dashboard.cache.LatestCurrencyDao
import com.metel20.data.load.cache.CurrenciesDao
import com.metel20.data.load.cache.CurrencyCache

@Database(
    entities = [CurrencyCache::class, CurrencyPairCache::class],
    version = 1,
    exportSchema = false
)
abstract class CurrenciesDatabase : RoomDatabase() {

    abstract fun currenciesDao(): CurrenciesDao

    abstract fun latestCurrencyDao(): LatestCurrencyDao
}
