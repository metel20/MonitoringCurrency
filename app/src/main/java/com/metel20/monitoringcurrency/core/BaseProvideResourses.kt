package com.metel20.monitoringcurrency.core

import android.content.Context
import com.metel20.data.core.ProvideResources
import com.metel20.monitoringcurrency.R

class BaseProvideResources(private val context: Context) : ProvideResources {

    override fun noInternetConnectionMessage(): String {
        return context.getString(R.string.no_internet_connection)
    }

    override fun serviceUnavailableMessage(): String {
        return context.getString(R.string.service_unavailable)
    }
}