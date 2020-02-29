package top.bilibililike.mvp

import android.content.Context

import okhttp3.Interceptor
import top.bilibililike.mvp.common.Preference
import top.bilibililike.mvp.http.RetrofitConfig


object DDComponent {

    lateinit var retrofit: RetrofitConfig

    fun init(context: Context, retrofit: RetrofitConfig) {

        this.retrofit = retrofit
        // 初始化 SharePreference
        Preference.init(context)
    }
}
