package com.osman.downloadtask.di

import android.app.Application
import com.osman.downloadtask.data.ReposImplementer
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [ReposeModule::class])
@Singleton
interface ApplicationComponent {

    @dagger.Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun getRepoImplmentor(): ReposImplementer
}