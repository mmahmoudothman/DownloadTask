package com.osman.downloadtask.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.*

object DataManager {

    fun getDataFromFileJson(context: Context): List<VideoModel> {
        val jsonFileString = getJsonDataFromAsset(context)
        val gson = Gson()
        val listPersonType = object : TypeToken<List<VideoModel>>() {}.type
        var items: List<VideoModel> = gson.fromJson(jsonFileString, listPersonType)
        return items;
    }

    private fun getJsonDataFromAsset(context: Context): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open("getListOfFilesResponse.json").bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }
}