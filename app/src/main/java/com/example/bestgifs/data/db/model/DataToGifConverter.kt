package com.example.bestgifs.data.db.model

import com.example.bestgifs.data.retrofit.model.Data

class DataToGifConverter(){

    fun getGif(data: Data): Gif{
        return Gif(data.images.downsized.gifUrl, null)
    }

    fun getGifs(datas: List<Data>): List<Gif>{
        val list = ArrayList<Gif>()
        for(data in datas) list.add(getGif(data))
        return list
    }
}