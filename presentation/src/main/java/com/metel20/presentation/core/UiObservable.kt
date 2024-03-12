package com.metel20.presentation.core

interface UiObservable<UiState : Any> : UpdateObserver<UiState>, UpdateUi<UiState> {

    abstract class Abstract<UiState : Any>(protected var cache: UiState) : UiObservable<UiState> {

        private var observer: UpdateUi<UiState> = UpdateUi.Empty()

        override fun updateObserver(observer: UpdateUi<UiState>) {
            this.observer = observer
            observer.updateUi(uiState = cache)
        }

        override fun updateUi(uiState: UiState) {
            cache = uiState
            observer.updateUi(cache)
        }
    }

}

interface UpdateObserver<UiState : Any> {
    fun updateObserver(observer: UpdateUi<UiState>)
}

interface UpdateUi<T : Any> {
    fun updateUi(uiState: T)

    class Empty<T : Any> : UpdateUi<T> {
        override fun updateUi(uiState: T) = Unit
    }
}