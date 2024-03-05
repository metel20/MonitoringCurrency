package com.metel20.data.dashboard.cloud

import com.google.gson.annotations.SerializedName

data class LatestCurrencyCloud(
    @SerializedName("rates")
    private val rates: HashMap<String, Double>
) {

    fun rate(currency: String): Double = rates.getValue(currency)

}