package com.example.bestgifs.data.db.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class Gif(@PrimaryKey(autoGenerate = true) var id: Long,
          @ColumnInfo(name = "file_path") var filePath: String)