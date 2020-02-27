package top.bilibililike.mvp.http

import retrofit2.Retrofit
import top.bilibililike.mvp.KtArmor

object RetrofitFactory{

    private val retrofit: Retrofit by lazy {
        KtArmor.retrofit.initRetrofit()
    }

    fun <T> create(clz: Class<T>): T {
        return retrofit.create(clz)
    }
}