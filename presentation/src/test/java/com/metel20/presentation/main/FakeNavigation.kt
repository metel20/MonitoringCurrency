package com.metel20.presentation.main

import com.metel20.presentation.core.Navigation
import com.metel20.presentation.core.Screen
import com.metel20.presentation.core.UpdateUi
import com.metel20.presentation.dashboard.DashboardScreen
import com.metel20.presentation.loading.LoadScreen
import org.junit.Assert.assertEquals


class FakeNavigation : Navigation {

    private var actual: Screen = Screen.Empty
    private var observer: UpdateUi<Screen> = UpdateUi.Empty()

    fun checkNavigateToLoad() {
        assertEquals(LoadScreen, actual)
    }

    fun checkNavigateToDashboard() {
        assertEquals(DashboardScreen, actual)
    }

    fun checkNotCalled() {
        assertEquals(Screen.Empty, actual)
    }

    override fun updateObserver(observer: UpdateUi<Screen>) {
        this.observer = observer
    }

    override fun updateUi(uiState: Screen) {
        actual = uiState
    }
}