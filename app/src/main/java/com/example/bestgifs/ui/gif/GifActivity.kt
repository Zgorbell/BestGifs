package com.example.bestgifs.ui.gif

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.arch.paging.PositionalDataSource
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
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
import android.widget.ImageView


class GifActivity : MvpAppCompatActivity(), GifMvpView {

//    private val TAG = GifActivity::class.java.simpleName
    private var gifAdapter: GifAdapter = GifAdapter(GifDiffUtilItemCallback())
    @InjectPresenter
    lateinit var gifPresenter: GifPresenter
    private lateinit var searchView: SearchView
    private lateinit var itemActionRefresh: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerViewGif.layoutManager = LinearLayoutManager(this)
        recyclerViewGif.adapter = gifAdapter
        RxSwipeRefreshLayout.refreshes(swipeRefreshLayout)
                .subscribe({gifPresenter.onRefreshSwiped()})
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_gif_main, menu)
        if (menu != null) {
            val actionSearch = menu.findItem(R.id.action_search)
            searchView = actionSearch.actionView as SearchView
            itemActionRefresh = menu.findItem(R.id.action_refresh)
//            searchView.setOnCloseListener {}
//            RxSearchView.queryTextChangeEvents(searchView).skipInitialValue().subscribe()
//            RxSearchView.queryTextChanges(searchView)
//                    .debounce(500, TimeUnit.MILLISECONDS)
//                    .subscribeOn(AndroidSchedulers.mainThread())
//                    .
        }
        prepareAdapter()
        return super.onCreateOptionsMenu(menu)
    }

    override fun activateItemActionRefresh(status: Boolean){
        if(status) {
            val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val iv = inflater.inflate(R.layout.iv_refresh, null) as ImageView
            val rotation = AnimationUtils.loadAnimation(this, R.anim.rotate_refresh)
            rotation.repeatCount = Animation.INFINITE
            iv.startAnimation(rotation)
            itemActionRefresh.actionView = iv
        } else {
            itemActionRefresh.actionView.clearAnimation()
            itemActionRefresh.actionView = null
        }
    }

    override fun invalidate() {
        gifAdapter.currentList!!.dataSource.invalidate()
    }

    override fun setSwipeRefreshStatus(status: Boolean) {
        swipeRefreshLayout.isRefreshing = status
    }



    private fun prepareAdapter() {
        getPagedLiveDataList().observe(this, Observer { gifAdapter.submitList(it) })
    }

    private fun getPagedListConfig(): PagedList.Config {
        return PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(30)
                .setInitialLoadSizeHint(30)
                .setPrefetchDistance(10)
                .build()
    }

    private fun getPagedLiveDataList(): LiveData<PagedList<Data>> {
        val config = getPagedListConfig()
        return LivePagedListBuilder<Int, Data>(CarDataSourceFactory(), config)
                .build()
    }

    inner class CarPositionalDataSource : PositionalDataSource<Data>() {

        override fun loadInitial(params: LoadInitialParams,
                                 callback: PositionalDataSource.LoadInitialCallback<Data>) {
            gifPresenter.onLoadInitial(params.pageSize, 0, callback)
        }

        override fun loadRange(params: LoadRangeParams
                               , callback: PositionalDataSource.LoadRangeCallback<Data>) {
            gifPresenter.onLoadRange(params.loadSize, params.startPosition, callback)
        }
    }

    inner class CarDataSourceFactory : DataSource.Factory<Int, Data>() {
        override fun create(): CarPositionalDataSource {
            return CarPositionalDataSource()
        }
    }

}
