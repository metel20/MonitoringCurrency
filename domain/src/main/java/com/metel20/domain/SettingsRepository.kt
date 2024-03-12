package com.metel20.domain

interface SettingsRepository {

    suspend fun getAllCurrencies(): List<String>

    suspend fun getAvailableDestinations(from: String): List<String>

    suspend fun save(from: String, to: String)
}