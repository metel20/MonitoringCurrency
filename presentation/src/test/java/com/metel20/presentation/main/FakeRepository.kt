package com.metel20.presentation.main

import com.metel20.domain.LoadCurrenciesRepository
import com.metel20.domain.LoadCurrenciesResult

class FakeRepository : LoadCurrenciesRepository {

    private lateinit var loadResult: LoadCurrenciesResult

    fun expectSuccess() {
        loadResult = LoadCurrenciesResult.Success
    }

    fun expectError() {
        loadResult = LoadCurrenciesResult.Error(message = "No internet connection")
    }

    override suspend fun loadCurrencies(): LoadCurrenciesResult {
        return loadResult
    }
}