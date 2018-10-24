package com.example.chintansoni.myapplication.model.remote.response

import com.google.gson.annotations.SerializedName

data class Photos(
    @SerializedName("perpage")
    val perpage: Int = 0,
    @SerializedName("total")
    val total: String = "",
    @SerializedName("pages")
    val pages: Int = 0,
    @SerializedName("photo")
    val photo: ArrayList<PhotoItem> = ArrayList(),
    @SerializedName("page")
    val page: Int = 0
)

data class PhotoItem(
    @SerializedName("owner")
    val owner: String = "",
    @SerializedName("server")
    val server: String = "",
    @SerializedName("ispublic")
    val ispublic: Int = 0,
    @SerializedName("isfriend")
    val isfriend: Int = 0,
    @SerializedName("farm")
    val farm: Int = 0,
    @SerializedName("id")
    val id: String = "",
    @SerializedName("secret")
    val secret: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("isfamily")
    val isfamily: Int = 0
)

data class FlickerSearchResponse(
    @SerializedName("stat")
    val stat: String = "",
    @SerializedName("photos")
    val photos: Photos
)


