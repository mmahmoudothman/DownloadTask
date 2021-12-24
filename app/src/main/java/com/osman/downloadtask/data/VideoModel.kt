package com.osman.downloadtask.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VideoModel(
    val id: Int,
    var name: String,
    val type: String,
    val url: String,
    var pathFileLocal: String? = null,
    var startDownload: Boolean = false,
    var isCompleted: Boolean = false,
    var isFailed: Boolean = false,
    var currentDownload: Long = 0,
    var totalFileSize: Long = Long.MAX_VALUE
) : Parcelable
