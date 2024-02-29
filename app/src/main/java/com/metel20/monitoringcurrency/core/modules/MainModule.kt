package com.metel20.monitoringcurrency.core.modules

import com.metel20.monitoringcurrency.core.Core
import com.metel20.presentation.main.MainViewModel

class MainModule(private val core: Core) : Module<MainViewModel> {

    override fun viewModel() = MainViewModel(navigation = core.provideNavigation())
}