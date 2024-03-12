package com.metel20.presentation.dashboard

import com.metel20.dashboard.DashboardItem
import com.metel20.dashboard.DashboardResult
import java.text.DecimalFormat

class BaseDashboardResultMapper(
    private val observable: DashboardUiObservable,
    private val dashboardItemMapper: DashboardItem.Mapper<DashboardUi> =
        BaseDashboardItemMapper()
) : DashboardResult.Mapper {

    override fun mapSuccess(listOfItem: List<DashboardItem>) {
        observable.updateUi(DashboardUiState.Base(listOfItem.map {
            it.map(dashboardItemMapper)
        }))
    }

    override fun mapError(message: String) {
        observable.updateUi(DashboardUiState.Error(message))
    }

    override fun mapEmpty() {
        observable.updateUi(DashboardUiState.Empty)
    }
}

class BaseDashboardItemMapper(

    private val rateFormat: RateFormat = RateFormat.Base(),
    private val currencyPairFormat: CurrencyPairFormat =
        CurrencyPairFormat.Base()
) : DashboardItem.Mapper<DashboardUi> {

    override fun map(fromCurrency: String, toCurrency: String, rates: Double) =
        DashboardUi.Base(
            currencyPairFormat.concat(fromCurrency, toCurrency), rateFormat.format(rates)
        )
}

interface CurrencyPairFormat {

    fun concat(from: String, to: String): String

    class Base(private val separator: String = "/") : CurrencyPairFormat {
        override fun concat(from: String, to: String): String {
            return from + separator + to
        }
    }
}

interface RateFormat {

    fun format(rates: Double): String

    class Base(pattern: String = "#.0#") : RateFormat {
        private val decimalFormat = DecimalFormat(pattern)
        override fun format(rates: Double): String = decimalFormat.format(rates)
    }
}