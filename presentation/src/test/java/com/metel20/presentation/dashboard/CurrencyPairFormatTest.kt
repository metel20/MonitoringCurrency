package com.metel20.presentation.dashboard

import org.junit.Assert.assertEquals
import org.junit.Test

class CurrencyPairFormatTest {

    @Test
    fun concatTest() {
        val currencyPair = CurrencyPairFormat.Base()
        val expected = "FROM/TO"
        val actual = currencyPair.concat("FROM", "TO")
        assertEquals(expected, actual)
    }

}