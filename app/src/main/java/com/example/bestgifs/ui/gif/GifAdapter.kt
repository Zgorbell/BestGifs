package com.example.bestgifs.ui.gif

import android.arch.paging.PagedListAdapter
import android.graphics.Bitmap
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.bestgifs.App
import com.example.bestgifs.R
import com.example.bestgifs.data.db.model.Gif
import kotlinx.android.synthetic.main.item_gif.view.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*

class GifAdapter(diffCallback: DiffUtil.ItemCallback<Gif>) :
        PagedListAdapter<Gif, GifAdapter.GifViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gif, parent, false)
        return GifViewHolder(view)
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class GifViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val TAG = GifViewHolder::class.java.simpleName

        fun bind(itemGif: Gif) = with(itemView) {
            progressBarItemGif.visibility = View.VISIBLE
            if (itemGif.filePath != null) {
                setImageGif(imageViewGif, itemGif)
                progressBarItemGif.visibility = View.GONE
            } else {
                Glide.with(context)
                        .asGif()
                        .load(itemGif.gifUrl)
                        .error(Glide.with(itemView).asGif().load(R.drawable.no_image))
                        .thumbnail(0.2f)
                        .into(imageViewGif)
                  progressBarItemGif.visibility = View.GONE
//                Observable.fromCallable{loadGif(imageViewGif, itemGif)}
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe{t: Gif? ->
//                            setImageGif(imageViewGif, t)
//                            progressBarItemGif.visibility = View.GONE
//                        }
            }

        }

        private fun setGif(byteArray: ByteArray, imageView: ImageView) {
            Glide.with(itemView)
                    .load(byteArray)
                    .into(imageView)
        }

        private fun setImageGif(imageView: ImageView, itemGif: Gif?) {
            Log.e(TAG, "and here")
            Glide.with(itemView)
                    .asGif()
                    .load(itemGif?.filePath)
                    .error(Glide.with(itemView).asGif().load(R.drawable.no_image))
                    .into(imageView)
        }

        private fun saveByteToFile(byteArray: ByteArray): File {
            val filesDir = App.getContext().getFilesDir()
            val imageFile = File(filesDir, (System.currentTimeMillis() + Random().nextLong()).toString())
            val os: OutputStream
            try {
                os = FileOutputStream(imageFile)
                os.write(byteArray)
                os.flush()
                os.close()
            } catch (e: Exception) {
                Log.e(TAG, "Error writing bitmap", e)
            }
            return imageFile
        }

        private fun loadNoImage(imageView: ImageView) {
            Glide.with(imageView).load(R.drawable.no_image).into(imageView)
        }

        private fun loadProgressGif(imageView: ImageView) {
            Glide.with(imageView).asGif().load(R.drawable.load).into(imageView)
        }
    }
}