package com.metel20.presentation.dashboard

import com.metel20.presentation.core.UiObservable

interface DashboardUiObservable : UiObservable<DashboardUiState> {
    class Base : UiObservable.Abstract<DashboardUiState>(DashboardUiState.Empty),
        DashboardUiObservable
}