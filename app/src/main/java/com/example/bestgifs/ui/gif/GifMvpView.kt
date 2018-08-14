package com.example.bestgifs.ui.gif

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface GifMvpView : MvpView{
    @StateStrategyType(SkipStrategy::class)
    fun invalidate()

    @StateStrategyType(SkipStrategy::class)
    fun setSwipeRefreshStatus(status: Boolean)

    @StateStrategyType(SkipStrategy::class)
    fun activateItemActionRefresh(status: Boolean)

    @StateStrategyType(SkipStrategy::class)
    fun showMessage(message: String)
}