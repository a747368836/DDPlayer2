package top.bilibililike.player.common

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import top.bilibililike.mvp.KtArmor

import top.bilibililike.player.common.api.MyRetrofitConfig


class MyApp : Application(){

    override fun onCreate() {
        super.onCreate()
        KtArmor.init(this, MyRetrofitConfig())
        mContext = applicationContext
    }

    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var mContext:Context
    }

}