package com.metel20.monitoringcurrency.core.modules

import com.metel20.presentation.core.CustomViewModel

interface Module<T : CustomViewModel> {

    fun viewModel(): T
}