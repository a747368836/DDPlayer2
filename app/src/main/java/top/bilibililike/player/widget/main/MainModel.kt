package top.bilibililike.player.widget.main

import androidx.room.Room
import top.bilibililike.mvp.http.RetrofitFactory
import top.bilibililike.mvp.mvp.BaseModel
import top.bilibililike.player.common.MyApp
import top.bilibililike.player.common.bean.BaseResponse
import top.bilibililike.player.common.bean.dao.TokenDataBase
import top.bilibililike.player.common.bean.dao.UserDataBase
import top.bilibililike.player.common.bean.userInfo.Data
import top.bilibililike.player.common.http.ApiManager
import top.bilibililike.player.common.http.bilibili.BiliService


/**
 *  @author: Xbs
 *  @date:   2020/02/29
 *  @desc:   TODO
 */

object MainModel : BaseModel() {
    fun getCacheInfo(): Data? = userInfoDataBase.getUserInfoDao().getUserObjectAsyc()

    suspend fun getUserInfo(): BaseResponse<Data> =
        launchIO { ApiManager.biliService.loadUserInfo().await() }


    //mode = LazyThreadSafetyMode.SYNCHRONIZED
    private val userInfoDataBase: UserDataBase by lazy() {
        Room.databaseBuilder(MyApp.mContext, UserDataBase::class.java, "user")
            .allowMainThreadQueries().build()
    }
}