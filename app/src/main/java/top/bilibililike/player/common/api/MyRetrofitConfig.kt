package top.bilibililike.player.common.api


import okhttp3.OkHttpClient
import retrofit2.Retrofit
import top.bilibililike.mvp.http.BaseOkHttpClient
import top.bilibililike.mvp.http.BaseRetrofit
import top.bilibililike.mvp.http.BaseRetrofitConfig
import top.bilibililike.player.common.BiliInterceptor

class MyRetrofitConfig : BaseRetrofitConfig() {
    override val baseUrl: String
        get() = Api.API_HOST



    override fun initRetrofit(): Retrofit {
        return BaseRetrofit.init()
    }

    override fun initOkHttpClient(): OkHttpClient {
        return BaseOkHttpClient.init(BiliInterceptor(null))
    }
}
