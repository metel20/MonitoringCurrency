package com.metel20.presentation.dashboard

import FakeRunAsync
import com.metel20.dashboard.DashboardItem
import com.metel20.dashboard.DashboardRepository
import com.metel20.dashboard.DashboardResult
import com.metel20.presentation.core.FakeClear
import com.metel20.presentation.core.FakeNavigation
import com.metel20.presentation.core.UpdateUi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test

class DashboardViewModelTest {
    private lateinit var navigation: FakeNavigation
    private lateinit var observable: FakeDashboardUiObservable
    private lateinit var repository: FakeDashboardRepository
    private lateinit var clear: FakeClear
    private lateinit var runAsync: FakeRunAsync
    private lateinit var viewModel: DashboardViewModel

    @Before
    fun setup() {
        navigation = FakeNavigation()
        observable = FakeDashboardUiObservable()
        repository = FakeDashboardRepository()
        clear = FakeClear()
        runAsync = FakeRunAsync()
        viewModel = DashboardViewModel(
            navigation = navigation,
            observable = observable,
            repository = repository,
            clear = clear,
            runAsync = runAsync,
        )
    }

    @Test
    fun empty() {
        repository.empty()
        viewModel.load()
        observable.checkProgress()
        runAsync.returnResult()
        observable.checkEmpty()
    }

    @Test
    fun errorThenSuccess() {
        repository.error()
        viewModel.load()
        observable.checkProgress()
        runAsync.returnResult()
        observable.checkError()

        repository.success()
        viewModel.retry()
        observable.checkProgress()
        runAsync.returnResult()
        observable.checkSuccess()
    }

    @Test
    fun settings() {
        viewModel.goToSettings()
        navigation.checkNavigateToSettings()
        clear.checkCalled(DashboardViewModel::class.java)
    }

    @Test
    fun lifecycle() {
        val observer: UpdateUi<DashboardUiState> = object : UpdateUi<DashboardUiState> {
            override fun updateUi(uiState: DashboardUiState) = Unit
        }
        viewModel.startGettingUpdates(observer = observer)
        observable.checkObserver(observer)

        viewModel.stopGettingUpdates()
        observable.checkEmptyObserver(observer)
    }
}

private class FakeDashboardRepository : DashboardRepository {


    private lateinit var result: DashboardResult

    override suspend fun dashboardItems(): DashboardResult {
        return result
    }

    fun empty() {
        result = DashboardResult.Empty
    }

    fun success() {
        result =
            DashboardResult.Success(
                listOf(
                    DashboardItem.Base(
                        from = "A",
                        to = "B",
                        rates = 123.40
                    )
                )
            )
    }

    fun error() {
        result = DashboardResult.Error(message = "No internet connection")
    }
}

private class FakeDashboardUiObservable : DashboardUiObservable {

    private var actualObserver: UpdateUi<DashboardUiState> = UpdateUi.Empty()
    private var actualUiState: DashboardUiState = DashboardUiState.Empty

    override fun updateObserver(observer: UpdateUi<DashboardUiState>) {
        this.actualObserver = observer
    }

    override fun updateUi(uiState: DashboardUiState) {
        actualUiState = uiState
    }

    fun checkProgress() {
        val expected: DashboardUiState = DashboardUiState.Progress
        assertEquals(expected, actualUiState)
    }

    fun checkEmpty() {
        val expected: DashboardUiState = DashboardUiState.Empty
        assertEquals(expected, actualUiState)
    }

    fun checkError() {
        val expected: DashboardUiState = DashboardUiState.Error(message = "No internet connection")
        assertEquals(expected, actualUiState)
    }

    fun checkSuccess() {
        val expected: DashboardUiState =
            DashboardUiState.Base(listOf(DashboardUi.Base("A/B", "123.40")))
        assertEquals(expected, actualUiState)
    }

    fun checkObserver(observer: UpdateUi<DashboardUiState>) {
        assertEquals(observer, actualObserver)
    }

    fun checkEmptyObserver(observer: UpdateUi<DashboardUiState>) {
        assertNotEquals(observer, actualObserver)
    }
}