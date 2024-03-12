package com.metel20.presentation.loading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.metel20.presentation.core.BaseFragment
import com.metel20.presentation.core.UpdateUi
import com.metel20.presentation.databinding.FragmentLoadBinding

class LoadFragment : BaseFragment<FragmentLoadBinding, LoadViewModel>() {

    private lateinit var updateUi: UpdateUi<LoadUiState>

    override fun inflate(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentLoadBinding.inflate(inflater, container, false)

    override val viewModelClass = LoadViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateUi = object : UpdateUi<LoadUiState> {
            override fun updateUi(uiState: LoadUiState) {
                uiState.update(
                    progressBar = binding.progress.progressBar,
                    errorTextView = binding.error.errorTextView,
                    retryButton = binding.error.retryButton
                )
            }
        }

        binding.error.retryButton.setOnClickListener {
            viewModel.load()
        }

        viewModel.init(isFirstRun = savedInstanceState == null)
    }

    override fun onResume() {
        super.onResume()
        viewModel.startGettingUpdates(observer = updateUi)
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopGettingUpdates()
    }
}