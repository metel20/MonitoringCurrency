package com.metel20.presentation.dashboard

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class BaseDashboardItemMapperTest {

    private val itemMapper: BaseDashboardItemMapper = BaseDashboardItemMapper()

    @Test
    fun successMap() {
        val expected = DashboardUi.Base(pair = "USD/EUR", rate = "2.0")
        val actual = itemMapper.map("USD", "EUR", 2.0)
        assertEquals(expected, actual)
    }

    @Test
    fun errorMap() {
        val expected = DashboardUi.Base(
            pair = "USB/ EUR", rate = "2.0"
        )
        val actual = itemMapper.map("USD", "EUR", 2.0)
        assertNotEquals(expected, actual)
    }
}

