package com.osman.downloadtask.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.osman.downloadtask.data.ReposImplementer

class MyViewModelFactory(val reposImplementer: ReposImplementer) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ReposImplementer::class.java)
            .newInstance(reposImplementer)

    }
}