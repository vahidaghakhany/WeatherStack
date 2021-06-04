package com.ramonapp.android.core.extension

import androidx.annotation.AnimRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

fun Fragment.replaceFragment(
    fragment: Fragment,
    container: Int, @AnimRes enterAnim: Int? = null, @AnimRes exitAnim: Int? = null,
    shouldAddToBackStack: Boolean = false,
    tag: String? = null
) {
    parentFragmentManager.transact {
        if (enterAnim != null && exitAnim != null) {
            setCustomAnimations(enterAnim, exitAnim)
        }
        replace(container, fragment, tag)
        if (shouldAddToBackStack) {
            addToBackStack(tag)
        }
    }
}

fun Fragment.replaceChildFragment(
    fragment: Fragment,
    container: Int, @AnimRes enterAnim: Int? = null, @AnimRes exitAnim: Int? = null,
    shouldAddToBackStack: Boolean = false,
    tag: String? = null
) {
    childFragmentManager.transact {
        if (enterAnim != null && exitAnim != null) {
            setCustomAnimations(enterAnim, exitAnim)
        }
        replace(container, fragment, tag)
        if (shouldAddToBackStack) {
            addToBackStack(tag)
        }
    }
}

inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commitAllowingStateLoss()
}