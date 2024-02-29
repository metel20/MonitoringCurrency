package com.metel20.presentation.main

import com.metel20.presentation.core.Clear
import com.metel20.presentation.core.CustomViewModel
import org.junit.Assert.assertEquals

class FakeClear : Clear {

    private var actual: Class<out CustomViewModel> = FakeViewModel::class.java

    override fun clear(clazz: Class<out CustomViewModel>) {
        actual = clazz
    }

    fun checkCalled(expected: Class<out CustomViewModel>) {
        assertEquals(expected, actual)
    }
}

object FakeViewModel : CustomViewModel