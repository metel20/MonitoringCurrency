package com.metel20.data.dashboard.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LatestCurrencyDao {

    @Query("select * from currency_table")
    suspend fun favoriteRates(): List<CurrencyPairCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(currency: CurrencyPairCache)
}