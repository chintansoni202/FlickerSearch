package com.example.chintansoni.myapplication.model

/**
 * Created by chintan.soni on 23/02/18.
 */
class Resource<out T>(val status: Status, val data: T?, val throwable: Throwable?) {

    companion object {

        fun <T> success(data: T? = null, throwable: Throwable? = null): Resource<T> {
            return Resource(Status.SUCCESS, data, throwable)
        }

        fun <T> error(data: T? = null, throwable: Throwable? = null): Resource<T> {
            return Resource(Status.ERROR, data, throwable)
        }

        fun <T> loading(data: T? = null, throwable: Throwable? = null): Resource<T> {
            return Resource(Status.LOADING, data, throwable)
        }
    }

    override fun toString(): String {
        return "Resource(status=$status, data=$data, throwable=$throwable)"
    }
}
