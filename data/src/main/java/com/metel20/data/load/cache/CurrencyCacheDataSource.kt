package com.metel20.data.load.cache

interface CurrencyCacheDataSource {

    interface Save {

        suspend fun save(currencies: HashMap<String, String>)
    }

    interface Read {

        suspend fun read(): List<CurrencyCache>
    }

    interface Mutable : Save, Read

    class Base(private val currenciesDao: CurrenciesDao) : Mutable {

        override suspend fun save(currencies: HashMap<String, String>) {
            currenciesDao.insert(currencies.map {
                CurrencyCache(it.key, it.value)
            })
        }

        override suspend fun read() = currenciesDao.currencies()
    }
}