package com.example.chintansoni.myapplication.view.adapter

import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.chintansoni.myapplication.R
import com.example.chintansoni.myapplication.databinding.ItemLoadingBinding
import com.example.chintansoni.myapplication.databinding.ItemPhotoBinding
import com.example.chintansoni.myapplication.model.remote.response.PhotoItem
import com.example.chintansoni.myapplication.util.PhotoItemDiffUtil
import com.example.chintansoni.myapplication.view.viewholder.LoaderViewHolder
import com.example.chintansoni.myapplication.view.viewholder.PhotoViewHolder

class FlickerRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list: ArrayList<PhotoItem> = ArrayList()
    private val ITEM_TYPE_NORMAL = 1
    private val ITEM_TYPE_LOADER = 2

    override fun getItemViewType(position: Int): Int {
        return if (list[position].id == "") {
            ITEM_TYPE_LOADER
        } else {
            ITEM_TYPE_NORMAL
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_TYPE_NORMAL) {
            val mUserBinding: ItemPhotoBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.context), R.layout.list_item_photo, parent, false)
            PhotoViewHolder(mUserBinding)
        } else {
            val mLoadingBinding: ItemLoadingBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.context), R.layout.list_item_loader, parent, false)
            LoaderViewHolder(mLoadingBinding)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder.itemViewType) {
            ITEM_TYPE_NORMAL -> {
                holder as PhotoViewHolder
                holder.setPhotoItem(list[position])
            }
            ITEM_TYPE_LOADER -> {
                // Do Nothing
            }
        }
    }

    fun setList(newList: ArrayList<PhotoItem>) {
        val diffResult = DiffUtil.calculateDiff(PhotoItemDiffUtil(newList, list))
        diffResult.dispatchUpdatesTo(this)
        list.clear()
        list.addAll(newList)
    }

    private fun getLoaderItem(): PhotoItem {
        return PhotoItem(id = "")
    }

    fun addLoader() {
        if (!isLoading()) {
            val newList = ArrayList<PhotoItem>(list)
            newList.add(getLoaderItem())
            setList(newList)
        }
    }

    fun removeLoader() {
        if (isLoading()) {
            if (!list.isEmpty()) {
                val newList = ArrayList<PhotoItem>(list)
                newList.remove(getLoaderItem())
                setList(newList)
            }
        }
    }

    fun isLoading(): Boolean {
        return list.isEmpty() || list.last().id == ""
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }
}