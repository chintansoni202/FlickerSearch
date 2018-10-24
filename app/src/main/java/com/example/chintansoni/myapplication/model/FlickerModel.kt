package com.example.chintansoni.myapplication.model

import com.example.chintansoni.myapplication.model.remote.ApiService
import com.example.chintansoni.myapplication.model.remote.response.FlickerSearchResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FlickerModel @Inject constructor(private var apiService: ApiService) {

    private var page: Int = 0
    private var text: String = ""

    fun fetchImages(text: String): Single<FlickerSearchResponse> {
        this.text = text
        this.page = 0
        return getFlickerApi(this.text, this.page)
    }

    fun getNextPageOfImages(): Single<FlickerSearchResponse> {
        page += 1
        return getFlickerApi(this.text, page)
    }

    private fun getFlickerApi(text: String, page: Int): Single<FlickerSearchResponse> {
        return apiService.getFlickerImages(text, page)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
}