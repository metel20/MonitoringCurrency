package com.metel20.monitoringcurrency.core

import com.metel20.monitoringcurrency.core.modules.ProvideModule
import com.metel20.presentation.core.CustomViewModel
import com.metel20.presentation.core.ProvideViewModel


class BaseProvideViewModel(private val provideModule: ProvideModule) : ProvideViewModel {

    override fun <T : CustomViewModel> viewModel(viewModelClass: Class<T>): T {
        return provideModule.module(viewModelClass).viewModel()
    }
}