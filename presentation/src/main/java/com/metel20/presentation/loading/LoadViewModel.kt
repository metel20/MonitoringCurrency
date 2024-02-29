package com.metel20.presentation.loading

import com.metel20.domain.LoadCurrenciesRepository
import com.metel20.domain.LoadCurrenciesResult
import com.metel20.presentation.core.BaseViewModel
import com.metel20.presentation.core.Clear
import com.metel20.presentation.core.Navigation
import com.metel20.presentation.core.RunAsync
import com.metel20.presentation.core.UpdateUi

class LoadViewModel(
    private val repository: LoadCurrenciesRepository,
    private val uiObservable: LoadUiObservable,
    private val navigation: Navigation,
    private val clear: Clear,
    runAsync: RunAsync,
    private val mapper: LoadCurrenciesResult.Mapper = BaseLoadResultMapper(
        uiObservable,
        navigation,
        clear
    )
) : BaseViewModel(runAsync) {

    fun init(isFirstRun: Boolean) {
        if (isFirstRun) load()
    }

    fun load() {
        uiObservable.updateUi(LoadUiState.Progress)
        runAsync({
            repository.loadCurrencies()
        }) {
            it.map(mapper)
        }
    }

    fun startGettingUpdates(observer: UpdateUi<LoadUiState>) {
        uiObservable.updateObserver(observer)
    }

    fun stopGettingUpdates() {
        uiObservable.updateObserver(UpdateUi.Empty())
    }
}