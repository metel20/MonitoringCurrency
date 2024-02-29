package com.metel20.presentation.core

interface Navigation : UiObservable<Screen> {

    class Base : UiObservable.Abstract<Screen>(Screen.Empty), Navigation
}