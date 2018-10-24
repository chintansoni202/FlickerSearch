package com.example.chintansoni.myapplication.model.remote

import com.example.chintansoni.myapplication.model.remote.response.FlickerSearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("services/rest")
    fun getFlickerImages(
        @Query("text") text: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 21,
        @Query("method") lang: String = "flickr.photos.search",
        @Query("api_key") apiKey: String = "3e7cc266ae2b0e0d78e279ce8e361736",
        @Query("format") format: String = "json",
        @Query("nojsoncallback") noJsonCallback: Int = 1,
        @Query("safe_search") safeSearch: Int = 1
    ): Single<FlickerSearchResponse>
}