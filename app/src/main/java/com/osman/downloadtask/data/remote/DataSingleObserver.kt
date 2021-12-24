package com.osman.downloadtask.data.remote

import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import timber.log.Timber

abstract class DataSingleObserver<T> : SingleObserver<T> {
    override fun onSubscribe(d: Disposable) {}
    override fun onSuccess(response: T) {
        onSuccessful(response)
    }

    override fun onError(e: Throwable) {
        Timber.e(e)
        onFailure(
            Throwable(e)
        )
    }

    abstract fun onSuccessful(response: T)
    abstract fun onFailure(e: Throwable?)

}