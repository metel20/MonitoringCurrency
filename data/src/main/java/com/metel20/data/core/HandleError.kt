package com.metel20.data.core

import java.net.UnknownHostException

interface HandleError {

    fun handleError(exception: Exception): String

    class Base(private val provideResources: ProvideResources) : HandleError {

        override fun handleError(exception: Exception): String = with(provideResources) {
            if (exception is UnknownHostException)
                noInternetConnectionMessage()
            else
                serviceUnavailableMessage()
        }
    }
}