package com

import com.metel20.data.BaseSettingsRepository
import com.metel20.data.dashboard.cache.CurrencyPairCache
import com.metel20.data.dashboard.cache.CurrencyPairCacheDataSource
import com.metel20.data.load.cache.CurrencyCache
import com.metel20.data.load.cache.CurrencyCacheDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BaseSettingsRepositoryTest {
    private lateinit var repository: BaseSettingsRepository
    private lateinit var favoriteCacheDataSource: FakeCurrencyPairCacheDataSource
    private lateinit var currencyCacheDataSource: FakeCurrencyCacheDataSource


    @Before
    fun setup() {
        currencyCacheDataSource = FakeCurrencyCacheDataSource()
        favoriteCacheDataSource = FakeCurrencyPairCacheDataSource()
        repository = BaseSettingsRepository(
            allItemsCacheDataSource = currencyCacheDataSource,
            favoriteCacheDataSource = favoriteCacheDataSource
        )
    }

    @Test
    fun scenarioTest() = runBlocking {
        val expectedCurrencyList = listOf("USD", "EUR", "JPY")
        val actualCurrencyList = repository.getAllCurrencies()
        assertEquals(expectedCurrencyList, actualCurrencyList)

        val actualFirst = repository.getAvailableDestinations("USD")
        val expectedFirst = listOf("EUR", "JPY")
        assertEquals(expectedFirst, actualFirst)

        repository.save("USD", "EUR")
        val actualSecond = repository.getAvailableDestinations("USD")
        val expectedSecond = listOf("JPY")
        assertEquals(expectedSecond, actualSecond)
        repository.save("USD", "JPY")

        val actualThird = repository.getAvailableDestinations("USD")
        val expectedThird = listOf<String>()
        assertEquals(expectedThird, actualThird)
    }
}

private class FakeCurrencyPairCacheDataSource() : CurrencyPairCacheDataSource.Mutable {

    private val list = mutableListOf<CurrencyPairCache>()

    override suspend fun save(currency: CurrencyPairCache) {
        list.add(currency)
    }

    override suspend fun read(): List<CurrencyPairCache> {
        return list
    }
}

private class FakeCurrencyCacheDataSource() : CurrencyCacheDataSource.Read {

    override suspend fun read(): List<CurrencyCache> {
        return listOf(
            CurrencyCache("USD", "DOLLAR"),
            CurrencyCache("EUR", "EURO"),
            CurrencyCache("JPY", "YEN"),
        )
    }
}


