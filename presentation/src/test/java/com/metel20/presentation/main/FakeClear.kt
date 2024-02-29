package com.metel20.presentation.main

import com.metel20.presentation.core.Clear
import com.metel20.presentation.core.CustomViewModel
import org.junit.Assert.assertEquals

class FakeClear : Clear {

    private var actual: Class<out CustomViewModel> = FakeViewModel::class.java
    private var count = 0

    override fun clear(clazz: Class<out CustomViewModel>) {
        actual = clazz
        count++
    }

    fun checkCalled(expected: Class<out CustomViewModel>) {
        assertEquals(expected, actual)
    }

    fun checkNotCalled() {
        assertEquals(0, count)
    }
}

object FakeViewModel : CustomViewModel