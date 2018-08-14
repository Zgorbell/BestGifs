package com.example.bestgifs.data.db.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class Gif(@ColumnInfo(name = "gif_url") var gifUrl: String,
          @ColumnInfo(name = "file_path") var filePath: String?){

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}