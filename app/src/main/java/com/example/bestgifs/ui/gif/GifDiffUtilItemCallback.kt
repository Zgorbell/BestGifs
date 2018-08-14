package com.example.bestgifs.ui.gif

import android.support.v7.util.DiffUtil
import com.example.bestgifs.data.db.model.Gif
import com.example.bestgifs.data.retrofit.model.Data
class GifDiffUtilItemCallback : DiffUtil.ItemCallback<Gif>() {


    override fun areItemsTheSame(oldItem: Gif?, newItem: Gif?): Boolean {
        return oldItem?.id == newItem?.id
    }

    override fun areContentsTheSame(oldItem: Gif?, newItem: Gif?): Boolean {
        return oldItem?.gifUrl == newItem?.gifUrl && oldItem?.filePath == newItem?.filePath
    }
}