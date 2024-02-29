package com.metel20.presentation.main

import com.metel20.presentation.core.UpdateUi
import com.metel20.presentation.loading.LoadUiObservable
import com.metel20.presentation.loading.LoadUiState
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals

class FakeUiObservable : LoadUiObservable {

    private var actualUiState: LoadUiState = LoadUiState.Empty
    private var actualObserver: UpdateUi<LoadUiState> = UpdateUi.Empty()
    private var count = 0

    override fun updateUi(uiState: LoadUiState) {
        actualUiState = uiState
        count++
    }

    override fun updateObserver(observer: UpdateUi<LoadUiState>) {
        actualObserver = observer
    }

    fun checkProgress() {
        val expected: LoadUiState = LoadUiState.Progress
        Assert.assertEquals(expected, actualUiState)
    }


    fun checkObserver(expected: UpdateUi<LoadUiState>) {
        Assert.assertEquals(expected, actualObserver)
    }


    fun checkError() {
        val expected: LoadUiState = LoadUiState.Error(message = "No internet connection")
        Assert.assertEquals(expected, actualUiState)
    }

    fun checkEmpty(expected: UpdateUi<LoadUiState>) {
        assertNotEquals(expected, actualObserver)
    }

    fun checkNotCalled() {
        assertEquals(0, count)
    }
}