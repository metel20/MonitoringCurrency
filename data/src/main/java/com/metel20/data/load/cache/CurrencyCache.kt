package com.metel20.data.load.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currencies_table")
data class CurrencyCache(
    @ColumnInfo("name")
    @PrimaryKey
    val name: String,
    @ColumnInfo("fullName")
    val fullName: String
)