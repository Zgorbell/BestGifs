package com.example.bestgifs.data.retrofit

import com.example.bestgifs.data.retrofit.model.GiphyResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GifApi{
    @GET("/v1/gifs/search")
    fun downloadGifSearch(@Header("api_key") api_key: String,
                    @Query("q") q: String,
                    @Query("limit") limit: Int,
                    @Query("offset") offset: Int): Single<GiphyResponse>

    @GET("/v1/gifs/trending")
    fun downloadGifTrending(@Query("api_key") api_key: String,
                    @Query("limit") limit: Int,
                    @Query("offset") offset: Int): Single<GiphyResponse >

}