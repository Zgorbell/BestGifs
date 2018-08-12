package com.example.bestgifs.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(val application: Application){

    @Provides
    @Singleton
    internal fun provideApplicationContext(): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    internal fun provideApplication(): Application {
        return application
    }
}