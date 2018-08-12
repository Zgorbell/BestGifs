package com.example.bestgifs.di.module

import android.arch.persistence.room.Room
import android.content.Context
import com.example.bestgifs.data.db.AppDatabase
import com.example.bestgifs.data.helper.GifReloadStateHelper
import com.example.bestgifs.ui.base.BaseRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class StorageModule(var databaseName:String){

    @Provides
    @Singleton
    fun provideDatabase(context: Context) : AppDatabase{
        return Room.databaseBuilder(context, AppDatabase::class.java,  databaseName).build()
    }

    @Provides
    @Singleton
    fun provideReloadGifStateHelper() : GifReloadStateHelper{
        return GifReloadStateHelper()
    }

    @Provides
    @Singleton
    fun provideBaseRepository(retrofit: Retrofit) : BaseRepository{
        return BaseRepository(retrofit)
    }
}