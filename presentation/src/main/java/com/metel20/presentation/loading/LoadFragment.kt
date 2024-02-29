package com.metel20.presentation.loading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.metel20.presentation.core.BaseFragment
import com.metel20.presentation.core.ProvideViewModel
import com.metel20.presentation.core.UpdateUi
import com.metel20.presentation.databinding.FragmentLoadBinding

class LoadFragment : BaseFragment<FragmentLoadBinding>() {

    private lateinit var viewModel: LoadViewModel
    private lateinit var updateUi: UpdateUi<LoadUiState>

    override fun inflate(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentLoadBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as ProvideViewModel).viewModel(LoadViewModel::class.java)
        updateUi = object : UpdateUi<LoadUiState> {
            override fun updateUi(uiState: LoadUiState) {
                uiState.update(
                    retryButton = binding.retryButton,
                    progressBar = binding.progressBar,
                    errorTextView = binding.errorTextView
                )
            }
        }
        viewModel.init(savedInstanceState == null)

        binding.retryButton.setOnClickListener {
            viewModel.load()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.startGettingUpdates(updateUi)
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopGettingUpdates()
    }
}


