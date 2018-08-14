package com.example.bestgifs.ui.gif

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.arch.paging.PositionalDataSource
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.bestgifs.R
import com.example.bestgifs.data.retrofit.model.Data
import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout
import kotlinx.android.synthetic.main.activity_main.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.LayoutInflater
import android.widget.Toast
import com.example.bestgifs.App
import com.example.bestgifs.data.db.model.Gif
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView
import com.jakewharton.rxbinding2.view.RxMenuItem
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit


class GifActivity : MvpAppCompatActivity(), GifMvpView {
    private val TAG = GifActivity::class.java.simpleName
    private var gifAdapter: GifAdapter = GifAdapter(GifDiffUtilItemCallback())
    @InjectPresenter
    lateinit var gifPresenter: GifPresenter
    private lateinit var itemActionRefresh: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(TAG, "on create")
        setContentView(R.layout.activity_main)
        recyclerViewGif.layoutManager = LinearLayoutManager(this)
        recyclerViewGif.adapter = gifAdapter
        RxSwipeRefreshLayout.refreshes(swipeRefreshLayout)
                .subscribe({ gifPresenter.onRefreshSwiped() })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Log.e(TAG, " on crate options menu")
        menuInflater.inflate(R.menu.menu_gif_main, menu)
        if (menu != null) {
            val actionSearch = menu.findItem(R.id.action_search)
            itemActionRefresh = menu.findItem(R.id.action_refresh)
            prepareAdapter()
            RxMenuItem.clicks(itemActionRefresh)
                    .subscribe({ gifPresenter.onRefreshClicked() })
            RxSearchView.queryTextChanges(actionSearch.actionView as SearchView)
                    .skip(2)
                    .debounce(500, TimeUnit.MILLISECONDS)
                    .doOnNext{t -> if(TextUtils.isEmpty(t)) gifPresenter.onSearchNone()}
                    .filter{t -> !TextUtils.isEmpty(t) }
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe{ t ->
                        if(TextUtils.isEmpty(t))  gifPresenter.onSearchNone()
                        else gifPresenter.onSearchChanged(t.toString())
                    }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun activateItemActionRefresh(status: Boolean) {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val iv = inflater.inflate(R.layout.iv_refresh, null)
        val rotation = AnimationUtils.loadAnimation(this, R.anim.rotate_refresh)
        rotation.repeatCount = Animation.INFINITE
        iv.startAnimation(rotation)
        if (status) {
            itemActionRefresh.actionView = iv
        } else if(itemActionRefresh.actionView != null) {
            itemActionRefresh.actionView.clearAnimation()
            itemActionRefresh.actionView = null
        }
    }

    override fun invalidate() {
        gifAdapter.currentList?.dataSource?.invalidate()
    }

    override fun setSwipeRefreshStatus(status: Boolean) {
        swipeRefreshLayout.isRefreshing = status
    }

    override fun showMessage(message: String) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT)
            Snackbar.make(recyclerViewGif, message, Snackbar.LENGTH_LONG).show()
        else Toast.makeText(this, message, Snackbar.LENGTH_LONG).show()
    }

    private fun prepareAdapter() {
        getPagedLiveDataList().observe(this, Observer { gifAdapter.submitList(it) })
    }

    private fun getPagedListConfig(): PagedList.Config {
        return PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(30)
                .setInitialLoadSizeHint(60)
                .setPrefetchDistance(20)
                .build()
    }

    private fun getPagedLiveDataList(): LiveData<PagedList<Gif>> {
        val config = getPagedListConfig()
        return LivePagedListBuilder<Int, Gif>(CarDataSourceFactory(), config)
                .build()
    }

    inner class CarPositionalDataSource : PositionalDataSource<Gif>() {

        override fun invalidate() {
            super.invalidate()
            gifPresenter.onInvalidate()
        }

        override fun loadInitial(params: LoadInitialParams,
                                 callback: PositionalDataSource.LoadInitialCallback<Gif>) {
            gifPresenter.onLoadInitial(params.pageSize, 0, callback)
        }

        override fun loadRange(params: LoadRangeParams
                               , callback: PositionalDataSource.LoadRangeCallback<Gif>) {
            gifPresenter.onLoadRange(params.loadSize, params.startPosition, callback)
        }
    }

    inner class CarDataSourceFactory : DataSource.Factory<Int, Gif>() {
        override fun create(): CarPositionalDataSource {
            return CarPositionalDataSource()
        }
    }

}
