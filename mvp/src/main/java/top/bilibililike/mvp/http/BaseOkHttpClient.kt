package top.bilibililike.mvp.http


import okhttp3.Interceptor
import okhttp3.OkHttpClient
import top.bilibililike.mvp.KtArmor
import top.bilibililike.mvp.http.intercept.LoggingIntercept
import java.util.concurrent.TimeUnit


object BaseOkHttpClient {

    // 初始化 okHttp
    fun init(vararg interceptors: Interceptor): OkHttpClient {

        return OkHttpClient.Builder().run {

            interceptors.forEach { addInterceptor(it) }

            addInterceptor(LoggingIntercept.init())
            readTimeout(KtArmor.retrofit.readTimeOut, TimeUnit.SECONDS)
            writeTimeout(KtArmor.retrofit.writeTimeOut, TimeUnit.SECONDS)
            connectTimeout(KtArmor.retrofit.connectTimeOut, TimeUnit.SECONDS)

            build()
        }
    }
}