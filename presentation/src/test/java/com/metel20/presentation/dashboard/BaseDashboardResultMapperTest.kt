package com.metel20.presentation.dashboard

import com.metel20.dashboard.DashboardItem
import com.metel20.presentation.core.UpdateUi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BaseDashboardResultMapperTest {
    private lateinit var observable: FakeObservable
    private lateinit var mapper: BaseDashboardResultMapper

    @Before
    fun setup() {
        observable = FakeObservable()
        mapper = BaseDashboardResultMapper(observable)
    }

    @Test
    fun mapSuccess() {
        mapper.mapSuccess(listOf(DashboardItem.Base("A", "B", 12.3)))
        assertEquals(
            DashboardUiState.Base(listOf(DashboardUi.Base("A/B", "12.3"))),
            observable.actual
        )
    }

    @Test
    fun mapError() {
        mapper.mapError("Error")
        assertEquals(DashboardUiState.Error("Error"), observable.actual)
    }

    @Test
    fun mapEmpty() {
        mapper.mapEmpty()
        assertEquals(DashboardUiState.Empty, observable.actual)
    }
}

private class FakeObservable : DashboardUiObservable {
    lateinit var actual: DashboardUiState

    override fun updateObserver(observer: UpdateUi<DashboardUiState>) {
    }

    override fun updateUi(uiState: DashboardUiState) {
        actual = uiState
    }
}