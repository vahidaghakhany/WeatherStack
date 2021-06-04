package com.ramonapp.android.core.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getContentView()?.let {
            setContentView(it)
            initView()
            subscribeViews()
            clickListeners()
            executeInitialTasks()
        } ?: kotlin.run { throw Exception("Layout not defined") }
    }


    fun resetBackStack() {
        for (i in 0 until supportFragmentManager.backStackEntryCount) {
            supportFragmentManager.popBackStack()
        }
    }

    abstract fun getContentView(): Int?
    abstract fun initView()
    abstract fun clickListeners()
    abstract fun subscribeViews()
    abstract fun executeInitialTasks()
}
