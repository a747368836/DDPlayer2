package top.bilibililike.mvp.http

import retrofit2.Retrofit
import top.bilibililike.mvp.DDComponent

object RetrofitFactory{

    private val retrofit: Retrofit by lazy {
        DDComponent.retrofit.initRetrofit()
    }

    fun <T> create(clz: Class<T>): T {
        return retrofit.create(clz)
    }
}