package com.example.bestgifs.di.component

import android.content.Context
import com.example.bestgifs.App
import com.example.bestgifs.data.db.AppDatabase
import com.example.bestgifs.data.helper.GifReloadStateHelper
import com.example.bestgifs.di.module.ApplicationModule
import com.example.bestgifs.di.module.NetModule
import com.example.bestgifs.di.module.StorageModule
import com.example.bestgifs.ui.base.BaseRepository
import com.example.bestgifs.ui.gif.GifPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [StorageModule::class, ApplicationModule::class, NetModule::class])
interface ApplicationComponent{

    fun inject(app: App)

    fun injectMvpPresenter(presenter: GifPresenter)

    fun getContext(): Context

    fun appDatabase() : AppDatabase

    fun reloadGifStateHelper() : GifReloadStateHelper

    fun baseRepository() : BaseRepository
}