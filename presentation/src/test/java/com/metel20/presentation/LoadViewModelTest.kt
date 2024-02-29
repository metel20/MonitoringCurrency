package com.metel20.presentation

import FakeRunAsync
import com.metel20.presentation.loading.BaseLoadResultMapper
import com.metel20.presentation.loading.LoadViewModel
import com.metel20.presentation.main.FakeClear
import com.metel20.presentation.main.FakeNavigation
import com.metel20.presentation.main.FakeRepository
import com.metel20.presentation.main.FakeUiObservable
import org.junit.Before
import org.junit.Test

class LoadViewModelTest {

    private lateinit var viewModel: LoadViewModel
    private lateinit var uiObservable: FakeUiObservable
    private lateinit var repository: FakeRepository
    private lateinit var runAsync: FakeRunAsync
    private lateinit var navigation: FakeNavigation
    private lateinit var clear: FakeClear

    @Before
    fun setup() {
        uiObservable = FakeUiObservable()
        navigation = FakeNavigation()
        repository = FakeRepository()
        runAsync = FakeRunAsync()
        clear = FakeClear()
        viewModel = LoadViewModel(
            repository = repository,
            uiObservable = uiObservable,
            navigation = navigation,
            clear = clear,
            runAsync = runAsync,
            mapper = BaseLoadResultMapper(uiObservable, navigation, clear)
        )
    }

    @Test
    fun testFirstRunErrorThenSuccess() {
        repository.expectError()
        viewModel.init(isFirstRun = true)
        uiObservable.checkProgress()
        runAsync.returnResult()
        uiObservable.checkError()

        repository.expectSuccess()
        viewModel.load()

        uiObservable.checkProgress()
        runAsync.returnResult()
        navigation.checkNavigateToDashboard()
        clear.checkCalled(LoadViewModel::class.java)
    }

    @Test
    fun notFirstRun() {
        viewModel.init(isFirstRun = false)
        uiObservable.checkNotCalled()
        navigation.checkNotCalled()
        clear.checkNotCalled()
    }
}