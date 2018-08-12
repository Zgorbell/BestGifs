package com.example.bestgifs.ui.gif

import android.support.v7.util.DiffUtil
import com.example.bestgifs.data.retrofit.model.Data
class GifDiffUtilItemCallback : DiffUtil.ItemCallback<Data>() {


    override fun areItemsTheSame(oldItem: Data?, newItem: Data?): Boolean {
        return false
    }

    override fun areContentsTheSame(oldItem: Data?, newItem: Data?): Boolean {
        return false
    }
}