package com.metel20.data

import com.metel20.data.core.ProvideResources
import com.metel20.data.load.BaseLoadCurrencyRepository
import com.metel20.data.load.cache.CurrencyCache
import com.metel20.data.load.cache.CurrencyCacheDataSource
import com.metel20.data.load.cloud.LoadCurrencyCloudDataSource
import com.metel20.domain.LoadCurrenciesResult
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

class BaseLoadCurrencyRepositoryTest {

    private lateinit var repository: BaseLoadCurrencyRepository
    private lateinit var cacheDataSource: FakeCurrencyCacheDataSource
    private lateinit var cloudDataSource: FakeLoadCurrencyCloudDataSource
    private lateinit var provideResources: FakeProvideResources

    @Before
    fun setup() {
        cacheDataSource = FakeCurrencyCacheDataSource()
        cloudDataSource = FakeLoadCurrencyCloudDataSource()
        provideResources = FakeProvideResources()
        repository = BaseLoadCurrencyRepository(
            cacheDataSource = cacheDataSource,
            cloudDataSource = cloudDataSource,
            provideResources = provideResources
        )
    }

    @Test
    fun successCloud() = runBlocking {
        cloudDataSource.successResult()
        cacheDataSource.noHaveCache()

        val actual = repository.loadCurrencies()
        val expected = LoadCurrenciesResult.Success
        assertEquals(expected, actual)
    }

    @Test
    fun successCache() = runBlocking {
        cloudDataSource.expectException(UnknownHostException())
        cacheDataSource.haveCache()

        val actual = repository.loadCurrencies()
        val expected = LoadCurrenciesResult.Success
        assertEquals(expected, actual)
    }

    @Test
    fun noConnection() = runBlocking {
        cloudDataSource.expectException(UnknownHostException())
        val actual = repository.loadCurrencies()
        val expected = LoadCurrenciesResult.Error(message = "No internet connection")
        assertEquals(expected, actual)
    }

    @Test
    fun serviceUnavailable() = runBlocking {
        cloudDataSource.expectException(IllegalStateException())
        val actual = repository.loadCurrencies()
        val expected = LoadCurrenciesResult.Error(message = "Service unavailable")
        assertEquals(expected, actual)
    }
}

private class FakeCurrencyCacheDataSource : CurrencyCacheDataSource.Mutable {

    private var actualList = emptyList<CurrencyCache>()
    private var haveCache = false


    fun haveCache() {
        haveCache = true
    }

    fun noHaveCache() {
        haveCache = false
    }


    override suspend fun save(currencies: List<CurrencyCache>) {
        actualList = currencies
    }

    override suspend fun read() = if (haveCache)
        listOf(
            CurrencyCache("USD", "United States Dollar"),
            CurrencyCache("AUD", "Australian Dollar"),
            CurrencyCache("JPY", "Japanese Yen")
        ) else
        emptyList()
}

private class FakeLoadCurrencyCloudDataSource : LoadCurrencyCloudDataSource {

    private var isSuccessResult: Boolean = false

    private lateinit var exception: Exception

    fun successResult() {
        isSuccessResult = true
    }

    fun expectException(exception: Exception) {
        this.exception = exception
    }

    override suspend fun currencies(): HashMap<String, String> {
        if (isSuccessResult)
            return hashMapOf(
                "USD" to "United States Dollar",
                "AUD" to "Australian Dollar",
                "JPY" to "Japanese Yen"
            )
        else
            throw exception
    }
}

private class FakeProvideResources : ProvideResources {

    override fun noInternetConnectionMessage() = "No internet connection"

    override fun serviceUnavailableMessage() = "Service unavailable"
}