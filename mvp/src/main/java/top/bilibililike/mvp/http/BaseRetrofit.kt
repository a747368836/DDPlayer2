package top.bilibililike.mvp.http


import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import top.bilibililike.mvp.DDComponent


object BaseRetrofit {

    fun init(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(DDComponent.retrofit.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(DDComponent.retrofit.initOkHttpClient())
            .build()
    }
}