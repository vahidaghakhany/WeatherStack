package com.ramonapp.android.weatherstack.main

import android.os.Bundle
import com.ramonapp.android.core.MainFragmentType
import com.ramonapp.android.core.base.BaseFragment
import com.ramonapp.android.core.extension.replaceChildFragment
import com.ramonapp.android.weatherstack.R
import com.ramonapp.android.weatherstack.setting.SettingFragment
import com.ramonapp.android.weatherstack.sevendays.DaysFragment
import com.ramonapp.android.weatherstack.today.TodayFragment


const val ARG_FRAGMENT_TYPE = "fragmentType"

class ContainerFragment : BaseFragment() {

    private var fragmentType: String? = null

    override fun getResId() = R.layout.fragment_container

    override fun initView() {
        arguments?.let {
            fragmentType = it.getString(ARG_FRAGMENT_TYPE)
        }
        initFragment()
    }

    private fun initFragment() {
        val fragment: BaseFragment? = when (fragmentType) {
            MainFragmentType.Today.name -> TodayFragment.newInstance()
            MainFragmentType.SevenDays.name -> DaysFragment.newInstance()
            MainFragmentType.Setting.name -> SettingFragment.newInstance()
            else -> null
        }
        fragment?.let {
            replaceChildFragment(it, R.id.containerFrame)
        }
    }

    override fun clickListeners() {
    }

    override fun subscribeViews() {
    }

    override fun executeInitialTasks() {
    }


    companion object {
        fun newInstance(fragment: String) =
            ContainerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_FRAGMENT_TYPE, fragment)
                }

            }
    }
}