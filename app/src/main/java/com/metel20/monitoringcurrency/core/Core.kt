package com.metel20.monitoringcurrency.core

import android.content.Context
import androidx.room.Room
import com.metel20.data.core.CurrenciesDatabase
import com.metel20.data.core.ProvideResources
import com.metel20.presentation.core.Navigation
import com.metel20.presentation.core.RunAsync
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface Core {

    fun provideNavigation(): Navigation

    fun provideResources(): ProvideResources

    fun provideRunAsync(): RunAsync

    fun provideDatabase(): CurrenciesDatabase

    fun provideRetrofit(): Retrofit

    class Base(context: Context) : Core {

        private val navigation by lazy { Navigation.Base() }

        private val provideResources by lazy { BaseProvideResources(context = context) }

        private val runAsync by lazy { RunAsync.Base() }

        private val db by lazy {
            Room.databaseBuilder(
                context,
                CurrenciesDatabase::class.java,
                "currencies_db"
            ).build()
        }

        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl("https://www.frankfurter.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(HttpLoggingInterceptor().apply {
                            setLevel(HttpLoggingInterceptor.Level.BODY)
                        }).build()
                ).build()
        }

        override fun provideDatabase() = db

        override fun provideRetrofit(): Retrofit = retrofit

        override fun provideNavigation() = navigation

        override fun provideResources() = provideResources

        override fun provideRunAsync() = runAsync
    }
}