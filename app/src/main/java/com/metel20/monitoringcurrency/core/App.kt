package com.metel20.monitoringcurrency.core

import android.app.Application
import com.metel20.monitoringcurrency.core.modules.ProvideModule
import com.metel20.presentation.core.Clear
import com.metel20.presentation.core.CustomViewModel
import com.metel20.presentation.core.ProvideViewModel


class App : Application(), ProvideViewModel {

    private lateinit var viewModelFactory: ProvideViewModel.Factory

    override fun onCreate() {
        super.onCreate()
        val clear = object : Clear {
            override fun clear(clazz: Class<out CustomViewModel>) {
                viewModelFactory.clear(clazz)
            }

        }
        val makeViewModule =
            BaseProvideViewModel(
                ProvideModule.Base(
                    core = Core.Base(this),
                    clear = clear
                )
            )
        viewModelFactory = ProvideViewModel.Factory(makeViewModule)
    }

    override fun <T : CustomViewModel> viewModel(clazz: Class<T>): T {
        return viewModelFactory.viewModel(clazz)
    }
}