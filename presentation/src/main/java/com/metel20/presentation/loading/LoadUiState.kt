package com.metel20.presentation.loading

import com.metel20.presentation.core.views.ChangeVisibility
import com.metel20.presentation.core.views.ErrorText

interface LoadUiState {

    fun update(
        progressBar: ChangeVisibility,
        errorTextView: ErrorText,
        retryButton: ChangeVisibility
    )

    data class Error(private val message: String) : LoadUiState {

        override fun update(
            progressBar: ChangeVisibility,
            errorTextView: ErrorText,
            retryButton: ChangeVisibility
        ) {
            errorTextView.show()
            errorTextView.change(message)
            progressBar.hide()
            retryButton.show()
        }
    }

    object Progress : LoadUiState {

        override fun update(
            progressBar: ChangeVisibility,
            errorTextView: ErrorText,
            retryButton: ChangeVisibility
        ) {
            errorTextView.hide()
            progressBar.show()
            retryButton.hide()
        }
    }

    object Empty : LoadUiState {

        override fun update(
            progressBar: ChangeVisibility,
            errorTextView: ErrorText,
            retryButton: ChangeVisibility
        ) = Unit
    }
}