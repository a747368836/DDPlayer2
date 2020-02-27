package top.bilibililike.mvp.http

import okhttp3.OkHttpClient
import retrofit2.Retrofit

interface RetrofitConfig {

    val baseUrl: String

    val readTimeOut: Long
    val writeTimeOut: Long
    val connectTimeOut: Long

    fun initRetrofit(): Retrofit

    fun initOkHttpClient(): OkHttpClient
}