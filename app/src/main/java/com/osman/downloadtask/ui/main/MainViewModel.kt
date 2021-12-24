package com.osman.downloadtask.ui.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.osman.downloadtask.data.DataManager
import com.osman.downloadtask.ui.base.BaseActivityViewModel
import com.osman.downloadtask.data.VideoModel
import java.util.ArrayList

class MainViewModel() : BaseActivityViewModel() {

    private val itemsList: MutableList<VideoModel> = ArrayList<VideoModel>()
    private val updateRecycler = MutableLiveData<Boolean>()

    fun getBranchList(context: Context): List<VideoModel> {
        return DataManager.getDataFromFileJson(context)
    }

    fun getUpdateRecycler(): LiveData<Boolean> {
        return updateRecycler
    }

    fun setUpdateRecycler(updateRecycler: Boolean) {
        this.updateRecycler.value = updateRecycler
    }
}