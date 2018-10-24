package com.example.chintansoni.myapplication.util

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.chintansoni.myapplication.R
import com.example.chintansoni.myapplication.model.remote.response.PhotoItem

object BindingUtils {
    @JvmStatic
    @BindingAdapter("app:photoItem")
    fun loadImage(view: ImageView, photoItem: PhotoItem) {
        GlideApp.with(view.context)
            .load(getUrl(photoItem))
            .placeholder(R.drawable.ic_place_holder)
            .thumbnail(0.4f)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(view)
    }

    private fun getUrl(photoItem: PhotoItem): String {
        return "http://farm${photoItem.farm}.static.flickr.com/${photoItem.server}/${photoItem.id}_${photoItem.secret}.jpg"
    }
}