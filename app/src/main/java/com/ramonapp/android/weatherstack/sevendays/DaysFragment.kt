package com.ramonapp.android.weatherstack.sevendays

import androidx.recyclerview.widget.LinearLayoutManager
import com.ramonapp.android.core.base.BaseFragment
import com.ramonapp.android.core.extension.observe
import com.ramonapp.android.core.extension.replaceChildFragment
import com.ramonapp.android.core.extension.replaceFragment
import com.ramonapp.android.weatherstack.R
import com.ramonapp.android.weatherstack.today.TodayFragment
import kotlinx.android.synthetic.main.fragment_days.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class DaysFragment : BaseFragment() {

    private val daysViewModel: DaysViewModel by viewModel()

    override fun getResId() = R.layout.fragment_days

    override fun initView() {

    }

    override fun clickListeners() {

    }

    override fun subscribeViews() {
        observe(daysViewModel.weatherListLv) {
            sevenDaysRcl.layoutManager = LinearLayoutManager(context)
            sevenDaysRcl.adapter = DaysAdapter(it, daysViewModel.getUnitType()) { weather ->
                replaceFragment(TodayFragment.newInstance(weather))
            }
        }
    }

    override fun executeInitialTasks() {
        daysViewModel.getSevenDays()
    }

    companion object {
        fun newInstance() = DaysFragment()
    }

}