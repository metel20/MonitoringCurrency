package com.metel20.dashboard

interface DashboardItem {

    fun <T : Any> map(mapper: Mapper<T>): T

    interface Mapper<T : Any> {
        fun map(fromCurrency: String, toCurrency: String, rates: Double): T
    }

    data class Base(
        private val fromCurrency: String,
        private val toCurrency: String,
        private val rates: Double
    ) : DashboardItem {
        override fun <T : Any> map(mapper: Mapper<T>): T =
            mapper.map(fromCurrency, toCurrency, rates)
    }
}