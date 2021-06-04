package com.ramonapp.android.core.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    private var mFragmentListener: IFragmentCallBack? = null
    var mRootView: View? = null
    private var isViewCreated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mRootView?.run {
            return this
        } ?: run {
            getResId()?.let {
                mRootView = inflater.inflate(it, container, false)
                return mRootView
            }

            throw Exception("Layout not defined")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isViewCreated) {
            initView()
            clickListeners()
            subscribeViews()
            executeInitialTasks()
            isViewCreated = true
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mFragmentListener = ((context) as IFragmentCallBack)
    }

    override fun onDetach() {
        super.onDetach()
        mFragmentListener = null
    }

    abstract fun getResId(): Int?

    fun replaceFragment(fragment: BaseFragment) {
        mFragmentListener?.replaceFragment(fragment)
    }

    fun replaceFragmentWithBackStack(fragment: BaseFragment) {
        mFragmentListener?.replaceFragmentWithBackStack(fragment)
    }

    override fun onDestroyView() {
        if (view != null) {
            val parentViewGroup = mRootView?.parent as ViewGroup?
            parentViewGroup?.removeAllViews()
        }
        super.onDestroyView()
    }

    abstract fun initView()
    abstract fun clickListeners()
    abstract fun subscribeViews()
    abstract fun executeInitialTasks()


}
