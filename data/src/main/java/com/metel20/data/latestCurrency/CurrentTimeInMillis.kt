package com.metel20.data.latestCurrency

interface CurrentTimeInMillis {
    fun time(): Long

    class Base : CurrentTimeInMillis {
        override fun time() = System.currentTimeMillis()
    }
}