package com.metel20.dashboard

interface DashboardRepository {

    suspend fun dashboardItems(): DashboardResult
}