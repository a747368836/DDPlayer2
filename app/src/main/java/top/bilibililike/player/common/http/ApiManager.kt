package top.bilibililike.player.common.http

import top.bilibililike.mvp.http.RetrofitFactory
import top.bilibililike.player.common.http.bilibili.AppService
import top.bilibililike.player.common.http.bilibili.LiveService

/**
 *  Project: DDPlayer
 *  Created by Xbs on 2020/2/28
 *  Describe:
 */

object ApiManager {
    val appService by lazy {
        RetrofitFactory.create(AppService::class.java)
    }

    val liveService by lazy {
        RetrofitFactory.create(LiveService::class.java)
    }
}
