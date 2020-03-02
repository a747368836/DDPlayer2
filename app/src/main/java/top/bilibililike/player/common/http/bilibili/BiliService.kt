package top.bilibililike.player.common.http.bilibili

import kotlinx.coroutines.Deferred
import retrofit2.http.*
import top.bilibililike.player.common.apiConfig.Api
import top.bilibililike.player.common.bean.BaseResponse
import top.bilibililike.player.common.bean.avDescription.AvDescriptionBean
import top.bilibililike.player.common.bean.live.LivePlayUrlBean
import top.bilibililike.player.common.bean.recommend.Data
import top.bilibililike.player.common.bean.search.SearchResultBean


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


    /**
     * 获取用户信息详情
     */
    @GET("https://app.bilibili.com/x/v2/account/myinfo")
    @Headers("${Api.DOMAIN_HEADER}:app.${Api.BILI_HEADER}")
    fun loadUserInfo():Deferred<BaseResponse<top.bilibililike.player.common.bean.userInfo.Data>>

    /**
     * 获取av类型视频的真实播放地址
     */
    @GET("https://app.bilibili.com/x/playurl")
    @Headers("${Api.DOMAIN_HEADER}:app.video.${Api.BILI_HEADER}")
    fun getVideoUrl(
        @Query("aid") aid:String,
        @Query("cid") cid:String,
        @Query("qn") qn:String
    ):Deferred<BaseResponse<top.bilibililike.player.common.bean.avUrl.Data>>


    /**
     * 获取视频视频的详细资料
     */
    @GET("https://app.bilibili.com/x/v2/view")
    @Headers("${Api.DOMAIN_HEADER}:app.${Api.BILI_HEADER}")
    fun getVideoDetail(@Query("aid")aid:String):Deferred<BaseResponse<AvDescriptionBean.DataBean>>


    /**
     * 直播播放的url地址
     */
    @GET("https://api.live.bilibili.com/xlive/app-room/v1/playUrl/playUrl")
    @Headers("${Api.DOMAIN_HEADER}:app.video.${Api.BILI_HEADER}")
    fun getLivePlayUrl(
        @Query("cid")roomId:String,
        @Query("https_url_req") urlReq:Int = 1,
        @Query("play_type")playType:Int = 0,
        @Query("ptype")pType:Int = 2,
        @Query("qn")qn:Int = 10000,
        @Query("unicom_free") free:Int = 0
    ):Deferred<BaseResponse<LivePlayUrlBean.DataBean>>


    /**
     * 搜索 综合
     */
    @GET("https://app.biliapi.net/x/v2/search")
    @Headers("${Api.DOMAIN_HEADER}:app.${Api.BILI_HEADER}")
    fun getSearchResult(
        @Query("keyword")keyword:String,
        @Query("pn")pageNumber:Int,
        @Query("ps")itemNum:Int,
        @Query("highlight")highLight:Int = 1
    ):Deferred<BaseResponse<SearchResultBean.DataBean>>


}