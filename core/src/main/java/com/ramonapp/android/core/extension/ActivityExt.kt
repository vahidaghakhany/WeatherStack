package com.ramonapp.android.core.extension

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.AnimRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.snackbar.Snackbar

fun AppCompatActivity.replaceFragment(
    fragment: Fragment,
    container: Int,
    @AnimRes enterAnim: Int? = null,
    @AnimRes exitAnim: Int? = null,
    shouldAddToBackStack: Boolean = false
) {
    supportFragmentManager.beginTransaction().apply {
        replace(container, fragment)
        if (shouldAddToBackStack) {
            addToBackStack("back_stack")
        }
        commitAllowingStateLoss()
    }
}


fun AppCompatActivity.replaceFragmentInActivity(
    fragment: Fragment, frameId: Int, fragmentManager: FragmentManager,
    stackName: String? = null, tagName: String? = null, hasBackStack: Boolean = false
) {
    if (hasBackStack) {
        fragmentManager.transact {
            replace(frameId, fragment, tagName)
            addToBackStack(stackName)
        }
    } else {
        fragmentManager.transact {
            replace(frameId, fragment, tagName)
        }
    }
}

fun Activity.hideKeyboard() {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
    currentFocus?.also { imm?.hideSoftInputFromWindow(it.windowToken, 0) }
}

fun Activity.showKeyboard() {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
    currentFocus?.also { imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0) }
}

fun Activity.showSnackBar(message:String) =  findViewById<View>(android.R.id.content).let { Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show() }
fun Activity.showSnackBar(stringResource:Int) =  findViewById<View>(android.R.id.content).let { Snackbar.make(it, stringResource, Snackbar.LENGTH_SHORT).show() }
