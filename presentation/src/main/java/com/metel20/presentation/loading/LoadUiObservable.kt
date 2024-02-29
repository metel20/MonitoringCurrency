package com.metel20.presentation.loading

import com.metel20.presentation.core.UiObservable

interface LoadUiObservable : UiObservable<LoadUiState> {
    class Base : UiObservable.Abstract<LoadUiState>(LoadUiState.Empty), LoadUiObservable
}