package com.example.bestgifs.data.helper

class GifReloadStateHelper(){
    var state: Int = RELOAD
    var search: String = ""
    companion object {
        const val RELOAD = 100
        @JvmStatic val RELOAD_SEARCH = 101

    }

    fun isReloadSearch() : Boolean{
        return state == RELOAD_SEARCH
    }
}