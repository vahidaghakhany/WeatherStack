package com.ramonapp.android.weatherstack.di


import com.ramonapp.android.weatherstack.setting.SettingViewModel
import com.ramonapp.android.weatherstack.sevendays.DaysViewModel
import com.ramonapp.android.weatherstack.today.TodayViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val module = module {

    viewModel {
        TodayViewModel(repository = get())
    }

    viewModel {
        DaysViewModel(repository = get())
    }

    viewModel {
        SettingViewModel()
    }
}

val diAppModules = listOf(module)
