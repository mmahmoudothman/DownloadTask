package com.osman.downloadtask.data.remote

import io.reactivex.rxjava3.core.Single
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface NagwaApiService {
    @Streaming
    @GET
    fun downloadFile(@Url fileUrl: String): Single<ResponseBody>
}