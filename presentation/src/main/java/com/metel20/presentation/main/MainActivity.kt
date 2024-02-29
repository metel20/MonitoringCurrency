package com.metel20.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.metel20.presentation.R
import com.metel20.presentation.core.CustomViewModel
import com.metel20.presentation.core.ProvideViewModel
import com.metel20.presentation.core.Screen
import com.metel20.presentation.core.UpdateUi

class MainActivity : AppCompatActivity(), ProvideViewModel {

    private lateinit var navigation: UpdateUi<Screen>
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = viewModel(MainViewModel::class.java)

        navigation = object : UpdateUi<Screen> {
            override fun updateUi(uiState: Screen) {
                uiState.show(R.id.container, supportFragmentManager)
            }
        }

        viewModel.init(savedInstanceState == null)
    }

    override fun onResume() {
        super.onResume()
        viewModel.startGettingUpdates(navigation)
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopGettingUpdates()
    }

    override fun <T : CustomViewModel> viewModel(viewModelClass: Class<T>): T {
        return (application as ProvideViewModel).viewModel(viewModelClass)
    }
}