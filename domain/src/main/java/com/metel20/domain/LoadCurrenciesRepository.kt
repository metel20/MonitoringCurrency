package com.metel20.domain

interface LoadCurrenciesRepository {
    suspend fun loadCurrencies(): LoadCurrenciesResult
}