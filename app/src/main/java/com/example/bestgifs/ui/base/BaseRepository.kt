package com.example.bestgifs.ui.base


import com.example.bestgifs.App
import com.example.bestgifs.data.retrofit.GifApi
import com.example.bestgifs.data.retrofit.model.GiphyResponse
import com.example.bestgifs.ui.gif.GifPresenter
import com.giphy.sdk.core.network.api.GPHApi
import com.giphy.sdk.core.network.api.GPHApiClient
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit


class BaseRepository(var retrofit : Retrofit){
    private val API_GYPHY_KEY = "KA2IeZ2h1iLNpW8kazuX0o8jXJqvs0FS"
    private lateinit var gifPresenter: GifPresenter

    fun setGifPresenter(gifPresenter: GifPresenter){
        this.gifPresenter = gifPresenter
    }

    fun downloadGif(count: Int, start: Int): Single<GiphyResponse> {
        return retrofit
                .create(GifApi::class.java)
                .downloadGifTrending(API_GYPHY_KEY, count, start)
    }

    fun downloadSearchGif(count: Int, start: Int, search: String): Single<GiphyResponse>{
        return retrofit.create(GifApi::class.java)
                .downloadGifSearch(API_GYPHY_KEY, search, count, start)
    }

    fun deleteAllFromDb(){
        Observable.fromCallable { App.getDatabase().photoDao().deleteAll() }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

}