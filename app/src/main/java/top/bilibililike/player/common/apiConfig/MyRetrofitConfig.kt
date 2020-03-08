package top.bilibililike.player.common.apiConfig


import okhttp3.OkHttpClient
import retrofit2.Retrofit
import top.bilibililike.mvp.http.BaseOkHttpClient
import top.bilibililike.mvp.http.BaseRetrofit
import top.bilibililike.mvp.http.BaseRetrofitConfig
import top.bilibililike.player.common.http.interceptors.BiliGetInterceptor
import top.bilibililike.player.common.http.interceptors.BiliPostInterceptor

class MyRetrofitConfig : BaseRetrofitConfig() {
    override val baseUrl: String
        get() = Api.APP_HOST



    override fun initRetrofit(): Retrofit {
        return BaseRetrofit.init()
    }

    override fun initOkHttpClient(): OkHttpClient {
        return BaseOkHttpClient.init(
            BiliGetInterceptor(
                null
            ), BiliPostInterceptor()
        )
    }
}
