package top.bilibililike.mvp.http


import okhttp3.Interceptor
import okhttp3.OkHttpClient
import top.bilibililike.mvp.DDComponent
import top.bilibililike.mvp.http.intercept.LoggingIntercept
import java.util.concurrent.TimeUnit


object BaseOkHttpClient {

    // 初始化 okHttp
    fun init(vararg interceptors: Interceptor): OkHttpClient {

        return OkHttpClient.Builder().run {

            interceptors.forEach { addInterceptor(it) }

            addInterceptor(LoggingIntercept.init())

            readTimeout(DDComponent.retrofit.readTimeOut, TimeUnit.SECONDS)
            writeTimeout(DDComponent.retrofit.writeTimeOut, TimeUnit.SECONDS)
            connectTimeout(DDComponent.retrofit.connectTimeOut, TimeUnit.SECONDS)

            build()
        }
    }

}