package top.bilibililike.mvp.http


import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import top.bilibililike.mvp.KtArmor


object BaseRetrofit {

    fun init(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(KtArmor.retrofit.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(KtArmor.retrofit.initOkHttpClient())
            .build()
    }
}