package com.example.bestgifs.ui.gif

import com.arellomobile.mvp.MvpView

interface GifMvpView : MvpView{
    fun invalidate()

    fun setSwipeRefreshStatus(status: Boolean)

    fun activateItemActionRefresh(status: Boolean)
}