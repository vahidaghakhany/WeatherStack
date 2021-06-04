package com.ramonapp.android.weatherstack.main

import com.ramonapp.android.core.MainFragmentType
import com.ramonapp.android.core.base.BaseActivity
import com.ramonapp.android.core.base.BaseFragment
import com.ramonapp.android.core.base.IFragmentCallBack
import com.ramonapp.android.core.extension.replaceFragment
import com.ramonapp.android.core.extension.replaceFragmentInActivity
import com.ramonapp.android.weatherstack.R
import com.ramonapp.android.weatherstack.customUI.bottomnavigation.events.OnSelectedItemChangeListener
import com.ramonapp.android.weatherstack.setting.SettingFragment
import com.ramonapp.android.weatherstack.sevendays.DaysFragment
import com.ramonapp.android.weatherstack.today.TodayFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity(), OnSelectedItemChangeListener, IFragmentCallBack {

    private var selectedTabItemPos = -1
    private var horizontalStack: MutableList<Int> = arrayListOf()


    private val todayFragment: ContainerFragment by lazy {
        ContainerFragment.newInstance(MainFragmentType.Today.name)
    }
    private val daysFragment: ContainerFragment by lazy {
        ContainerFragment.newInstance(MainFragmentType.SevenDays.name)
    }
    private val settingFragment: ContainerFragment by lazy {
        ContainerFragment.newInstance(MainFragmentType.Setting.name)
    }

    override fun getContentView() = R.layout.activity_main

    override fun initView() {
        setupBottomNavigation()
    }

    override fun clickListeners() {
    }

    override fun subscribeViews() {
    }

    override fun executeInitialTasks() {
    }


    private fun setupBottomNavigation() {
        bottomNavigation.setOnSelectedItemChangeListener(this)

        tabToday.itemPosition = TAB_TODAY_POSITION
        tabSevenDays.itemPosition = TAB_SEVEN_DAYS_POSITION
        tabSetting.itemPosition = TAB_SETTING_POSITION
    }

    override fun onSelectedItemChanged(itemId: Int) {
        when (itemId) {
            R.id.tabToday -> {
                replace(todayFragment, tabToday.position)
            }
            R.id.tabSevenDays -> {
                replace(daysFragment, tabSevenDays.position)
            }
            R.id.tabSetting -> {
                replace(settingFragment, tabSetting.position)
            }
        }
    }

    private fun replace(fragment: BaseFragment, itemPosition: Int) {

        if (selectedTabItemPos != itemPosition) {
            selectedTabItemPos = itemPosition
            horizontalStack.remove(selectedTabItemPos)
            horizontalStack.add(selectedTabItemPos)
            replaceFragment(fragment, R.id.mainFrame, shouldAddToBackStack = true)
        }
    }

    override fun onBackPressed() {
        val childFragmentManager = when (selectedTabItemPos) {
            TAB_TODAY_POSITION -> todayFragment.childFragmentManager
            TAB_SEVEN_DAYS_POSITION -> daysFragment.childFragmentManager
            TAB_SETTING_POSITION -> settingFragment.childFragmentManager
            else -> null
        }

        if (childFragmentManager == null) {
            supportFinishAfterTransition()
            return
        }

        if (childFragmentManager.backStackEntryCount >= 1) {
            childFragmentManager.popBackStack()
        } else {
            if (horizontalStack.size > 1) {
                //supportFragmentManager.popBackStack()
                horizontalStack.removeAt(horizontalStack.size - 1)
                bottomNavigation.selectedItem = horizontalStack[horizontalStack.size - 1]
            } else {
                supportFinishAfterTransition()
            }
        }
    }

    override fun replaceFragment(fragment: BaseFragment) {
        when (selectedTabItemPos) {
            TAB_TODAY_POSITION -> {
                    replaceFragmentInActivity(
                        fragment,
                        R.id.containerFrame,
                        todayFragment.childFragmentManager,
                        hasBackStack = true
                    )
            }

            TAB_SEVEN_DAYS_POSITION -> {
                    replaceFragmentInActivity(
                        fragment,
                        R.id.containerFrame,
                        daysFragment.childFragmentManager,
                        hasBackStack = true
                    )
            }

            TAB_SETTING_POSITION -> {
                    replaceFragmentInActivity(
                        fragment,
                        R.id.containerFrame,
                        settingFragment.childFragmentManager,
                        hasBackStack = true
                    )
            }
        }
    }

    override fun replaceFragmentWithBackStack(fragment: BaseFragment) {
    }

    override fun replaceFragmentWithRoot() {
    }


    companion object {
        const val TAB_TODAY_POSITION: Int = 0
        const val TAB_SEVEN_DAYS_POSITION: Int = 1
        const val TAB_SETTING_POSITION: Int = 2
    }


}