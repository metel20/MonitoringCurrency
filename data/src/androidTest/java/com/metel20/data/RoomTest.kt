package com.metel20.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.metel20.data.load.cache.CurrencyCache
import com.metel20.data.load.cache.CurrencyDao
import com.metel20.data.load.cache.CurrencyDataBase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class RoomTest {

    private lateinit var db: CurrencyDataBase
    private lateinit var dao: CurrencyDao

    @Before
    fun setup() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, CurrencyDataBase::class.java)
            .allowMainThreadQueries().build()
        dao = db.currencyDao()
    }

    @After
    @Throws(IOException::class)
    fun clear() {
        db.close()
    }

    @Test
    fun test() = runBlocking {
        assertEquals(emptyList<CurrencyCache>(), dao.currencies())
        val expected = listOf(
            CurrencyCache("USD", "United States Dollar"),
            CurrencyCache("AUD", "Australian Dollar"),
            CurrencyCache("JPY", "Japanese Yen")
        )
        dao.insert(expected)
        assertEquals(expected, dao.currencies())
    }
}