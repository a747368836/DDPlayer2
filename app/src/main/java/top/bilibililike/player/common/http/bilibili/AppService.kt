package top.bilibililike.player.common.http.bilibili

import retrofit2.http.GET
import retrofit2.http.Query
import kotlinx.coroutines.Deferred
import top.bilibililike.player.common.bean.BaseResponse
import top.bilibililike.player.common.bean.recommend.Data


/**
 *  Project: DDPlayer
 *  Created by Xbs on 2020/2/28
 *  Describe:
 */

interface AppService {

    @GET("https://app.bilibili.com/x/v2/feed/index")
    fun getRecommendList(
        @Query("pull") isRefresh: Boolean
        , @Query("column") column: Int = 2
    ): Deferred<BaseResponse<Data>>
}