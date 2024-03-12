package com.metel20.monitoringcurrency.core.modules

import com.metel20.monitoringcurrency.core.Core
import com.metel20.presentation.core.Clear
import com.metel20.presentation.core.CustomViewModel
import com.metel20.presentation.dashboard.DashboardViewModel
import com.metel20.presentation.loading.LoadViewModel
import com.metel20.presentation.main.MainViewModel

interface ProvideModule {

    fun <T : CustomViewModel> module(clazz: Class<T>): Module<T>

    class Base(
        private val clear: Clear,
        private val core: Core
    ) : ProvideModule {

        override fun <T : CustomViewModel> module(clazz: Class<T>): Module<T> {
            return when (clazz) {
                MainViewModel::class.java -> MainModule(core = core)
                LoadViewModel::class.java -> LoadModule(
                    core = core,
                    clear = clear
                )

                DashboardViewModel::class.java -> DashboardModule(core, clear)

                else -> throw IllegalStateException("unknown viewModel $clazz")
            } as Module<T>
        }
    }
}