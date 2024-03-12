package com.metel20.presentation.dashboard

import org.junit.Assert.assertEquals
import org.junit.Test

class RateFormatTest {

    @Test
    fun round() {
        val rateFormat = RateFormat.Base()
        val expected = "1.23"
        val actual = rateFormat.format(1.23456)
        assertEquals(expected, actual)
    }

    @Test
    fun zeroRound() {
        val rateFormat = RateFormat.Base()
        val expected = "2.0"
        val actual = rateFormat.format(2.0)
        assertEquals(expected, actual)
    }

    @Test
    fun exampleTest() {
        val rateFormat = RateFormat.Base()
        val expected = "123.4"
        val actual = rateFormat.format(123.4)
        assertEquals(expected, actual)
    }

}