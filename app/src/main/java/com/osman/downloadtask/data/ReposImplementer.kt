package com.osman.downloadtask.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.osman.downloadtask.data.model.VideoModel
import javax.inject.Inject

class ReposImplementer @Inject constructor(val context: Context) {

    fun getDataFromFileJson(): List<VideoModel> {
        val jsonString: String = context.assets.open("getListOfFilesResponse.json").bufferedReader()
            .use { it.readText() }
        val gson = Gson()
        val listPersonType = object : TypeToken<List<VideoModel>>() {}.type
        val items: List<VideoModel> = gson.fromJson(jsonString, listPersonType)
        return items; }
}