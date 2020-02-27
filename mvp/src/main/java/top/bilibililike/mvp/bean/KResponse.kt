package top.bilibililike.mvp.bean;


interface KResponse<T> {

    fun isSuccess(): Boolean

    fun getKMessage(): String

    fun getKData(): T?
}