package com.metel20.data.dashboard

import com.metel20.dashboard.DashboardItem
import com.metel20.dashboard.DashboardResult
import com.metel20.data.core.FakeHandleError
import com.metel20.data.core.FakeProvideResources
import com.metel20.data.dashboard.cache.CurrencyPairCache
import com.metel20.data.dashboard.cache.CurrencyPairCacheDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

class BaseDashboardRepositoryTest {

    private lateinit var repository: BaseDashboardRepository
    private lateinit var cacheDataSource: FakeCacheDataSource
    private lateinit var currencyPairRatesDataSource: FakeCurrencyPairRatesDataSource

    @Before
    fun setup() {
        cacheDataSource = FakeCacheDataSource()
        currencyPairRatesDataSource = FakeCurrencyPairRatesDataSource()
        repository = BaseDashboardRepository(
            cacheDataSource = cacheDataSource,
            currencyPairRatesDataSource = currencyPairRatesDataSource,
            handleError = FakeHandleError(FakeProvideResources())
        )
    }

    @Test
    fun testEmpty() = runBlocking {
        cacheDataSource.empty()
        val expected = repository.dashboardItems()
        assertEquals(expected, DashboardResult.Empty)
    }

    @Test
    fun testSuccess() = runBlocking {
        cacheDataSource.notEmpty()
        currencyPairRatesDataSource.success()
        val expected = repository.dashboardItems()
        assertEquals(
            expected, DashboardResult.Success(
                listOf(
                    DashboardItem.Base("A", "B", 1.0),
                    DashboardItem.Base("C", "D", 2.0)
                )
            )
        )
    }

    @Test
    fun testError() = runBlocking {
        cacheDataSource.notEmpty()
        currencyPairRatesDataSource.error()
        val expected = repository.dashboardItems()
        assertEquals(expected, DashboardResult.Error("No internet connection"))
    }
}

private class FakeCacheDataSource : CurrencyPairCacheDataSource.Read {

    private lateinit var data: List<CurrencyPairCache>

    override suspend fun read() = data

    fun empty() {
        data = listOf()
    }

    fun notEmpty() {
        data = listOf(
            CurrencyPairCache("A", "B", 1.0, 0L),
            CurrencyPairCache("C", "D", 2.0, 0L)
        )
    }
}

private class FakeCurrencyPairRatesDataSource : CurrencyPairRatesDataSource {

    private var isSuccess = true

    fun success() {
        isSuccess = true
    }

    fun error() {
        isSuccess = false
    }

    override suspend fun data(favoriteRates: List<CurrencyPairCache>) =
        if (isSuccess)
            favoriteRates.map {
                DashboardItem.Base(
                    it.from,
                    it.to,
                    it.rate
                )
            } else throw UnknownHostException()
}
