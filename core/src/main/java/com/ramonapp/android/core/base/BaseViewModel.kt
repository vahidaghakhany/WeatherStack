package com.ramonapp.android.core.base

import androidx.lifecycle.ViewModel
import com.ramonapp.android.core.base.APIResult
import com.shatelland.namava.common.core.mvvm.SingleLiveEvent

open class BaseViewModel : ViewModel() {
    var mError = SingleLiveEvent<String>()


    override fun onCleared() {
        super.onCleared()
    }

}