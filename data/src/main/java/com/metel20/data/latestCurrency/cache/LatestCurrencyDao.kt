package com.metel20.data.latestCurrency.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LatestCurrencyDao {

    @Query("SELECT * FROM currency_table")
    suspend fun favoriteRates(): List<LatestCurrencyCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(currency: LatestCurrencyCache)
}