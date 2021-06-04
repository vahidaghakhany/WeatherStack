package com.ramonapp.android.core.base


interface IFragmentCallBack {
    fun replaceFragment(fragment: BaseFragment)
    fun replaceFragmentWithBackStack(fragment: BaseFragment)
    fun replaceFragmentWithRoot()
}