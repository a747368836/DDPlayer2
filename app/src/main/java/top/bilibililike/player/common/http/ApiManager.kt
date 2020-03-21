package top.bilibililike.player.common.http

import top.bilibililike.mvp.http.RetrofitFactory
import top.bilibililike.player.common.http.bilibili.BiliService

/**
 *  Project: DDPlayer
 *  Created by Xbs on 2020/2/28
 *  Describe:
 */

object ApiManager {
    val biliService by lazy {
        RetrofitFactory.create(BiliService::class.java)
    }
}
