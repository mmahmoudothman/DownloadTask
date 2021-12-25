package com.osman.downloadtask.ui.main

import com.osman.downloadtask.data.ReposImplementer
import com.osman.downloadtask.ui.base.BaseActivityViewModel
import com.osman.downloadtask.data.model.VideoModel

class MainViewModel constructor(private val reposImplementer: ReposImplementer) :
    BaseActivityViewModel() {

    fun getBranchList(): List<VideoModel> {
        return reposImplementer.getDataFromFileJson()
    }
}