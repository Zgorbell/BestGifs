package com.example.bestgifs.data.db.room

import android.arch.persistence.room.*
import android.util.Log
import com.example.bestgifs.App
import com.example.bestgifs.data.db.model.Gif
import io.reactivex.Single
import java.io.File

@Dao
abstract class GifDao{

    @Insert
    abstract fun insert(gif: Gif)

    @Delete
    abstract fun delete(gifs: List<Gif>)

    @Query("Select * from gif order by id desc limit :limit offset :offset")
    abstract fun getGifs(limit: Int, offset: Int): List<Gif>

    @Query("Select * from gif")
    abstract fun getGifs(): List<Gif>

    @Transaction
    open fun deleteAll(){
        Log.e("TAG", "start delete")
        val gifs = getGifs()
        for(gif in gifs) File(gif.filePath).delete()
        delete(gifs)
    }


}