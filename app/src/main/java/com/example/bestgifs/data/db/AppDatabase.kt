package com.example.bestgifs.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.bestgifs.data.db.model.Gif
import com.example.bestgifs.data.db.room.GifDao

@Database(entities = [Gif::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun photoDao(): GifDao

}