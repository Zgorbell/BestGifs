package com.example.bestgifs.ui.gif

import android.arch.paging.PositionalDataSource
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.bestgifs.App
import com.example.bestgifs.data.db.model.DataToGifConverter
import com.example.bestgifs.data.db.model.Gif
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

    fun onRefreshClicked() {
        viewState.invalidate()
    }

    fun onInvalidate(){
        baseRepository.deleteAllFromDb()
    }

    fun onSearchNone() {
        gifReloadStateHelper.state = GifReloadStateHelper.RELOAD
        viewState.invalidate()
    }

    fun onSearchChanged(search: String) {
        gifReloadStateHelper.search = search
        gifReloadStateHelper.state = GifReloadStateHelper.RELOAD_SEARCH
        viewState.invalidate()
    }

    fun onLoadInitial(count: Int, start: Int, callback: PositionalDataSource.LoadInitialCallback<Gif>) {
        Log.e(TAG, "load initial $start $count")
        AndroidSchedulers.mainThread().createWorker().schedule({
            viewState.activateItemActionRefresh(true)
        })
        val adapterGif = DataToGifConverter()
        var list = App.getDatabase().photoDao().getGifs(count, start)
        list = if (!list.isEmpty()) list
        else if (!gifReloadStateHelper.isReloadSearch()) adapterGif.getGifs(downloadGif(count, start))
        else adapterGif.getGifs(downloadGifSearch(count, start))
        AndroidSchedulers.mainThread().createWorker().schedule({
            viewState.setSwipeRefreshStatus(false)
            viewState.activateItemActionRefresh(false)
        })
        callback.onResult(list, start)
    }

    fun onLoadRange(count: Int, start: Int, callback: PositionalDataSource.LoadRangeCallback<Gif>) {
        Log.e(TAG, "load range $start $count")
        AndroidSchedulers.mainThread().createWorker().schedule({
            viewState.activateItemActionRefresh(true)
        })
        val adapterGif = DataToGifConverter()
        var list = App.getDatabase().photoDao().getGifs(count, start)
        list = if (!list.isEmpty()) list
        else if (!gifReloadStateHelper.isReloadSearch()) adapterGif.getGifs(
                downloadGif(count, start + count + 1))
        else adapterGif.getGifs(downloadGifSearch(count, start + count + 1))
        AndroidSchedulers.mainThread().createWorker().schedule({
            viewState.setSwipeRefreshStatus(false)
            viewState.activateItemActionRefresh(false)
        })
        callback.onResult(list)
    }

    private fun downloadGif(count: Int, start: Int): List<Data> {
        var listMedia = ArrayList<Data>()
        baseRepository.downloadGif(count, start)
                .subscribe(object : SingleObserver<GiphyResponse> {
                    override fun onSuccess(result: GiphyResponse) {
                        if (result.data != null) {
                            listMedia = result.data as ArrayList<Data>
                        } else {
                            AndroidSchedulers.mainThread()
                                    .createWorker()
                                    .schedule({ viewState.showMessage("No results found") })
                        }
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        AndroidSchedulers.mainThread()
                                .createWorker()
                                .schedule({ viewState.showMessage("No internet connection") })
                    }
                })
        return listMedia
    }

    private fun downloadGifSearch(count: Int, start: Int): List<Data> {
        var listMedia = ArrayList<Data>()
        baseRepository.downloadSearchGif(count, start, gifReloadStateHelper.search)
                .subscribe(object : SingleObserver<GiphyResponse> {
                    override fun onSuccess(result: GiphyResponse) {
                        if (result.data != null) {
                            listMedia = result.data as ArrayList<Data>
                        } else {
                            AndroidSchedulers.mainThread()
                                    .createWorker()
                                    .schedule({ viewState.showMessage("No results found") })
                        }
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        AndroidSchedulers.mainThread()
                                .createWorker()
                                .schedule({ viewState.showMessage("No internet connection") })
                    }
                })
        return listMedia
    }
}