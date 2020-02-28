package top.bilibililike.mvp.bean;


interface Response<T> {

    fun isSuccess(): Boolean

    fun getMessage(): String

    fun getRawData(): T?

    fun getTs():Int?
}