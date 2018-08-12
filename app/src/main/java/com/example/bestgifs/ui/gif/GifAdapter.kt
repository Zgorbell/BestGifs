package com.example.bestgifs.ui.gif

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.bestgifs.R
import com.example.bestgifs.data.retrofit.model.Data
import kotlinx.android.synthetic.main.item_gif.view.*

class GifAdapter(diffCallback: DiffUtil.ItemCallback<Data>) :
        PagedListAdapter<Data, GifAdapter.GifViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gif, parent, false)
        return GifViewHolder(view)
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class GifViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Data) = with(itemView) {
            Glide.with(itemView).asGif().load(R.drawable.load).into(imageViewGif)
            Glide.with(itemView)
                    .asGif()
                    .load(item.images.downsized.gifUrl)
                    .into(imageViewGif)
        }
    }
}