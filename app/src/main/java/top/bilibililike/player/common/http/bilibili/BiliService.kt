package top.bilibililike.player.common.http.bilibili

import kotlinx.coroutines.Deferred
import retrofit2.http.*
import top.bilibililike.player.common.apiConfig.Api
import top.bilibililike.player.common.bean.BaseResponse
import top.bilibililike.player.common.bean.recommend.Data


/**
 *  Project: DDPlayer
 *  Created by Xbs on 2020/2/28
 *  Describe:
 */

interface BiliService {

    /**
     * @usage 首页推荐数据
     * @param pull 下拉刷新/上拉加载
     * @param column 首页recyclerview分几栏 B站客户端内有设置
     */
    @GET("https://app.bilibili.com/x/v2/feed/index")
    @Headers("${Api.DOMAIN_HEADER}: app.${Api.BILI_HEADER}")
    fun getRecommendList(
        @Query("pull") isRefresh: Boolean
        , @Query("column") column: Int = 2
    ): Deferred<BaseResponse<Data>>


    /**
     * 获取登录的RSA公钥 简化固定了URL，起码一年前就是这个了，问题不大
     */
    @POST("https://passport.bilibili.com/api/oauth2/getKey?appkey=1d8b6e7d45233436&sign=17004c193f688f0b5665c1068e733aff")
    @Headers("${Api.DOMAIN_HEADER}:passport.${Api.BILI_HEADER}")
    fun getRSAKey(): Deferred<BaseResponse<top.bilibililike.player.common.bean.login.Data>>

    /**
     * 真实登录，获取Token，有几个Header现在必须加上了，具体那几个没试
     */
    @FormUrlEncoded
    @POST("https://passport.bilibili.com/api/v3/oauth2/login")
    @Headers(
        "${Api.DOMAIN_HEADER}:passport.${Api.BILI_HEADER}",
        "APP-KEY:android",
        "env:prod",
        "User-Agent:Mozilla/5.0 BiliDroid/5.54.0 (bbcallen@gmail.com) os/android model/MI 6 mobi_app/android build/5540400 channel/xiaomi innerVer/5540400 osVer/9 network/2"
        )
    fun getToken(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("ts")ts:Int
    ):Deferred<BaseResponse<top.bilibililike.player.common.bean.login.token.Data>>



    @GET("https://app.bilibili.com/x/v2/account/myinfo")
    @Headers("${Api.DOMAIN_HEADER}:app.${Api.BILI_HEADER}")
    fun loadUserInfo():Deferred<BaseResponse<top.bilibililike.player.common.bean.userInfo.Data>>
}