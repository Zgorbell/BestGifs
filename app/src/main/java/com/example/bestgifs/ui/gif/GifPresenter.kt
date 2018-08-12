package com.example.bestgifs.ui.gif

import android.arch.paging.PositionalDataSource
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.bestgifs.App
import com.example.bestgifs.data.helper.GifReloadStateHelper
import com.example.bestgifs.data.retrofit.model.Data
import com.example.bestgifs.data.retrofit.model.GiphyResponse
import com.example.bestgifs.ui.base.BaseRepository
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@InjectViewState
class GifPresenter : MvpPresenter<GifMvpView>() {
    private val TAG = GifPresenter::class.java.simpleName
    @Inject
    lateinit var baseRepository: BaseRepository
    @Inject
    lateinit var gifReloadStateHelper: GifReloadStateHelper

    init {
        App.applicationComponent.injectMvpPresenter(this)
    }

    fun onRefreshSwiped() {
        viewState.invalidate()
    }

    fun onLoadInitial(count: Int, start: Int, callback: PositionalDataSource.LoadInitialCallback<Data>) {
        Log.e(TAG, "load initial $start $count")
        AndroidSchedulers.mainThread()
                .createWorker()
                .schedule({ viewState.activateItemActionRefresh(true) })
        if (gifReloadStateHelper.isReloadSearch())
            callback.onResult(downloadGifSearch(count, start), start)
        else callback.onResult(downloadGif(count, start), start)
        AndroidSchedulers.mainThread()
                .createWorker()
                .schedule({ viewState.setSwipeRefreshStatus(false)
            viewState.activateItemActionRefresh(false)
        })
    }

    fun onLoadRange(count: Int, start: Int, callback: PositionalDataSource.LoadRangeCallback<Data>) {
        Log.e(TAG, "load range $start $count")
        AndroidSchedulers.mainThread()
                .createWorker()
                .schedule({ viewState.activateItemActionRefresh(true) })
        if (gifReloadStateHelper.isReloadSearch())
            callback.onResult(downloadGifSearch(count, start + count + 1))
        else callback.onResult(downloadGif(count, start + count + 1))
        AndroidSchedulers.mainThread().createWorker().schedule({
            viewState.setSwipeRefreshStatus(false)
            viewState.activateItemActionRefresh(false)
        })
    }

    private fun downloadGif(count: Int, start: Int): List<Data> {
        var listMedia = ArrayList<Data>()
        baseRepository.downloadGif(count, start)
                .subscribe(object : SingleObserver<GiphyResponse> {
                    override fun onSuccess(result: GiphyResponse) {
                        if (result == null) {
                            Log.e(TAG, "Error")
                        } else {
                            if (result.data != null) {
                                listMedia = result.data as ArrayList<Data>
                            } else {
                                Log.e("giphy error", "No results found")
                            }
                        }
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        Log.e(TAG, e.toString())
                    }
                })
        return listMedia
    }

    private fun downloadGifSearch(count: Int, start: Int): List<Data> {
        var listMedia = ArrayList<Data>()
        baseRepository.downloadSearchGif(count, start, gifReloadStateHelper.search)
                .subscribe(object : SingleObserver<GiphyResponse> {
                    override fun onSuccess(result: GiphyResponse) {
                        Log.e(TAG, "here")
                        if (result == null) {
                            Log.e(TAG, "Error")
                        } else {
                            if (result.data != null) {
                                listMedia = result.data as ArrayList<Data>
                            } else {
                                Log.e("giphy error", "No results found")
                            }
                        }
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                    }
                })
        return listMedia
    }
}