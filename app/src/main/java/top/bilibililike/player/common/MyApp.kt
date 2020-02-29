package top.bilibililike.player.common

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import top.bilibililike.mvp.DDComponent

import top.bilibililike.player.common.apiConfig.MyRetrofitConfig


class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        DDComponent.init(this, MyRetrofitConfig())
        mContext = applicationContext
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var mContext: Context


    }


}