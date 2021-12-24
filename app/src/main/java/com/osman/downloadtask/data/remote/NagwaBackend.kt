package com.osman.downloadtask.data.remote

import androidx.databinding.ktx.BuildConfig
import com.google.gson.GsonBuilder
import io.reactivex.rxjava3.core.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

object NagwaBackend : NagwaApiService {

    private var webServices: NagwaApiService? = null

    fun createApiConnection() {
        createApiConnection(createOkHttpClientBuilder().build())
    }

    private fun createApiConnection(okHttpClient: OkHttpClient) {
        webServices = Retrofit.Builder()
            .baseUrl(Config.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(NagwaApiService::class.java)
    }

    private fun createOkHttpClientBuilder(): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(60, TimeUnit.SECONDS)
        builder.callTimeout(60, TimeUnit.SECONDS)
        builder.readTimeout(60, TimeUnit.SECONDS)
        builder.writeTimeout(60, TimeUnit.SECONDS)
        builder.retryOnConnectionFailure(false)
        builder.interceptors().add(Interceptor { chain ->
            val request = chain.request()

            var response = chain.proceed(request)
            var tryCount = 0
            while (!response.isSuccessful && tryCount < 3) {
                tryCount++
                response = chain.proceed(request)
            }
            response
        })
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor { message: String? ->
                Timber.i(
                    message
                )
            }
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
        }
        return builder
    }

    override fun downloadFile(fileUrl: String): Single<ResponseBody> {
        return webServices!!.downloadFile(fileUrl)
    }

}