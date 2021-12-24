package com.osman.downloadtask.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseActivityViewModel : ViewModel() {

    private val showLoadingProgress = MutableLiveData<Boolean>()
    private val failMessage = MutableLiveData<String>()

    fun getShowLoadingProgress(): LiveData<Boolean> {
        return showLoadingProgress
    }

    fun setShowLoadingProgress(showLoadingProgress: Boolean) {
        this.showLoadingProgress.value = showLoadingProgress
    }

    fun getFailMessage(): LiveData<String> {
        return failMessage
    }

    fun setFailMessage(failMessage: String?) {
        setShowLoadingProgress(false)
        this.failMessage.value = failMessage
    }
}