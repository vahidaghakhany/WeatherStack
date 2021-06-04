package com.ramonapp.android.core.di


import android.content.Context
import com.ramonapp.android.core.data.ApiClient
import com.ramonapp.android.core.repository.Repository
import com.ramonapp.android.core.repository.RequestApi
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

private val module = module {

    single {
        Repository(ApiClient.getRetrofitInstance().create(RequestApi::class.java))
    }

    single {
        androidApplication().getSharedPreferences("DEFAULT_PREF", Context.MODE_PRIVATE)
    }
}

val diModules = listOf(module)
