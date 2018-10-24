package com.example.chintansoni.myapplication.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.chintansoni.myapplication.model.FlickerModel
import com.example.chintansoni.myapplication.model.Resource
import com.example.chintansoni.myapplication.model.remote.response.FlickerSearchResponse
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class SearchActivityViewModel @Inject constructor(private var flickerModel: FlickerModel) : ViewModel() {

    private var flickerImagesLiveData: MutableLiveData<Resource<FlickerSearchResponse>> = MutableLiveData()
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun getLiveData(): LiveData<Resource<FlickerSearchResponse>> = flickerImagesLiveData

    fun getFlicker(text: String) {
        showLoading()
        val disposable = flickerModel.fetchImages(text)
            .subscribe({
                if (it.photos.total.toInt() == 0) {
                    flickerImagesLiveData.value = Resource.error(throwable = Throwable("No data available"))
                } else {
                    flickerImagesLiveData.value = Resource.success(it)
                }
            }, {
                flickerImagesLiveData.value = Resource.error(throwable = it)
            })
        compositeDisposable.add(disposable)
    }

    fun getNextPageImages() {
        showLoading()
        val disposable = flickerModel.getNextPageOfImages()
            .subscribe({ it ->
                it.photos.photo.let {
                    flickerImagesLiveData.value?.data?.photos?.photo?.addAll(it.asIterable())
                }
                flickerImagesLiveData.value = Resource.success(flickerImagesLiveData.value?.data)
            }, {
                flickerImagesLiveData.value = Resource.error(throwable = it)
            })
        compositeDisposable.add(disposable)
    }

    private fun showLoading() {
        flickerImagesLiveData.value =
                Resource.loading(flickerImagesLiveData.value?.data, flickerImagesLiveData.value?.throwable)
    }
}
