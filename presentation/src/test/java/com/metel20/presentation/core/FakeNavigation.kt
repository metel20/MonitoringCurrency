package com.metel20.presentation.core

import com.metel20.presentation.dashboard.DashboardScreen
import com.metel20.presentation.loading.LoadScreen
import com.metel20.presentation.settings.SettingsScreen
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

    fun checkNavigateToSettings() {
        assertEquals(SettingsScreen, actual)
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