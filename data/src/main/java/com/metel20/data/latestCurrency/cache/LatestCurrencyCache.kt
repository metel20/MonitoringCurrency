package com.metel20.data.latestCurrency.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.metel20.data.latestCurrency.CurrentTimeInMillis
import java.util.concurrent.TimeUnit

@Entity(tableName = "currency_table", primaryKeys = ["from", "to"])
data class LatestCurrencyCache(

    @ColumnInfo("from")
    val from: String,
    @ColumnInfo("to")
    val to: String,
    @ColumnInfo("rate")
    val rate: Double,
    @ColumnInfo("lastUpdate")
    val lastUpdate: Long
) {
    fun isOutdated(currentTimeInMillis: CurrentTimeInMillis) = rate == -1.0 ||
            (currentTimeInMillis.time() - lastUpdate) >= TimeUnit.HOURS.toMillis(24)
}