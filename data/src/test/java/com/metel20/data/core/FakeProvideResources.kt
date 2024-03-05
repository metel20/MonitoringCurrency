package com.metel20.data.core


class FakeProvideResources : ProvideResources {
    override fun noInternetConnectionMessage(): String {
        return "No internet connection"
    }

    override fun serviceUnavailableMessage(): String {
        return "Service unavailable"
    }
}

