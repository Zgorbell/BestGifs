package com.example.bestgifs

import android.app.Application
import android.content.Context
import com.example.bestgifs.data.db.AppDatabase
import com.example.bestgifs.di.component.ApplicationComponent
import com.example.bestgifs.di.component.DaggerApplicationComponent
import com.example.bestgifs.di.module.ApplicationModule
import com.example.bestgifs.di.module.NetModule
import com.example.bestgifs.di.module.StorageModule

class App : Application() {
    private var DATABASE_NAME: String = "database"
    private var BASE_GIPHY_URL: String = "http://api.giphy.com"

    companion object {
        lateinit var applicationComponent: ApplicationComponent
            private set

        fun getContext() = applicationComponent.getContext()

        fun getDatabase() = applicationComponent.appDatabase()
    }

    init {
        applicationComponent = DaggerApplicationComponent.builder()
                .netModule(NetModule(BASE_GIPHY_URL))
                .applicationModule(ApplicationModule(this))
                .storageModule(StorageModule(DATABASE_NAME))
                .build()

        applicationComponent.inject(this)
    }

}