package com.example.chintansoni.myapplication.util

import android.support.v7.util.DiffUtil
import com.example.chintansoni.myapplication.model.remote.response.PhotoItem

class PhotoItemDiffUtil(
    private val newList: ArrayList<PhotoItem>? = null,
    private val oldList: ArrayList<PhotoItem>? = null
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList?.get(oldItemPosition)?.id == newList?.get(newItemPosition)?.id
    }

    override fun getOldListSize(): Int {
        return oldList?.size ?: 0
    }

    override fun getNewListSize(): Int {
        return newList?.size ?: 0
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList?.get(oldItemPosition) === newList?.get(newItemPosition)
    }
}
