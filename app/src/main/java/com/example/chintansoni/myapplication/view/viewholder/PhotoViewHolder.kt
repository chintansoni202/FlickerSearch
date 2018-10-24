package com.example.chintansoni.myapplication.view.viewholder

import android.support.v7.widget.RecyclerView
import com.example.chintansoni.myapplication.databinding.ItemPhotoBinding
import com.example.chintansoni.myapplication.model.remote.response.PhotoItem

class PhotoViewHolder(var mUserBinding: ItemPhotoBinding) : RecyclerView.ViewHolder(mUserBinding.root) {

    fun setPhotoItem(photoItem: PhotoItem) {
        mUserBinding.photoItem = photoItem
    }
}