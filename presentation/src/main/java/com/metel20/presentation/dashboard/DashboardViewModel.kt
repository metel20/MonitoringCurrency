package com.metel20.presentation.dashboard

import com.metel20.dashboard.DashboardRepository
import com.metel20.dashboard.DashboardResult
import com.metel20.presentation.core.BaseViewModel
import com.metel20.presentation.core.Clear
import com.metel20.presentation.core.Navigation
import com.metel20.presentation.core.RunAsync
import com.metel20.presentation.core.UpdateUi
import com.metel20.presentation.settings.SettingsScreen

class DashboardViewModel(
    private val navigation: Navigation,
    private val observable: DashboardUiObservable,
    private val repository: DashboardRepository,
    private val clear: Clear,
    runAsync: RunAsync,
    private val mapper: DashboardResult.Mapper = BaseDashboardResultMapper(observable)
) : BaseViewModel(runAsync), Retry {

    fun load() {
        observable.updateUi(DashboardUiState.Progress)
        runAsync({
            repository.dashboardItems()
        }) {
            it.map(mapper)
        }
    }

    fun goToSettings() {
        navigation.updateUi(SettingsScreen)
        clear.clear(DashboardViewModel::class.java)
    }

    override fun retry() {
        load()
    }

    fun startGettingUpdates(observer: UpdateUi<DashboardUiState>) {
        observable.updateObserver(observer)
    }

    fun stopGettingUpdates() {
        observable.updateObserver(UpdateUi.Empty())
    }
}
