package top.bilibililike.mvp.http


import okhttp3.OkHttpClient
import retrofit2.Retrofit
import top.bilibililike.mvp.common.Setting


abstract class BaseRetrofitConfig : RetrofitConfig {

    override val readTimeOut: Long
        get() = Setting.READ_TIME_OUT

    override val writeTimeOut: Long
        get() = Setting.WRITE_TIME_OUT

    override val connectTimeOut: Long
        get() = Setting.CONNECT_TIME_OUT

    override fun initRetrofit(): Retrofit = BaseRetrofit.init()

    override fun initOkHttpClient(): OkHttpClient = BaseOkHttpClient.init()
}