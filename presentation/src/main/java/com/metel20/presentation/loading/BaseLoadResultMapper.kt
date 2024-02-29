package com.metel20.presentation.loading

import com.metel20.domain.LoadCurrenciesResult
import com.metel20.presentation.core.Clear
import com.metel20.presentation.core.Navigation
import com.metel20.presentation.core.UpdateUi
import com.metel20.presentation.dashboard.DashboardScreen

class BaseLoadResultMapper(
    private val observable: UpdateUi<LoadUiState>,
    private val navigation: Navigation,
    private val clear: Clear
) : LoadCurrenciesResult.Mapper {

    override fun mapError(message: String) {
        observable.updateUi(LoadUiState.Error(message))
    }

    override fun mapSuccess() {
        navigation.updateUi(DashboardScreen)
        clear.clear(LoadViewModel::class.java)
    }


}