package com.metel20.presentation.main

import com.metel20.presentation.core.CustomViewModel
import com.metel20.presentation.core.Navigation
import com.metel20.presentation.core.Screen
import com.metel20.presentation.core.UpdateUi
import com.metel20.presentation.loading.LoadScreen

class MainViewModel(
    private val navigation: Navigation,
) : CustomViewModel {

    fun init(isFirstRun: Boolean) {
        if (isFirstRun) {
            navigation.updateUi(LoadScreen)
        }
    }

    fun startGettingUpdates(navigation: UpdateUi<Screen>) {
        this.navigation.updateObserver(navigation)
    }

    fun stopGettingUpdates() {
        navigation.updateObserver(UpdateUi.Empty())
    }
}