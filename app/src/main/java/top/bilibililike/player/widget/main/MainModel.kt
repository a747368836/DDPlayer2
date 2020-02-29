package top.bilibililike.player.widget.main

import androidx.room.Room
import top.bilibililike.mvp.mvp.BaseModel
import top.bilibililike.player.common.MyApp
import top.bilibililike.player.common.bean.BaseResponse
import top.bilibililike.player.common.bean.userInfo.Data
import top.bilibililike.player.common.dao.userDataBase
import top.bilibililike.player.common.http.ApiManager


/**
 *  @author: Xbs
 *  @date:   2020/02/29
 *  @desc:
 */

object MainModel : BaseModel() {

    suspend fun getCacheInfo():Data? = userDataBase.getUserInfoDao().getUserObject()

    suspend fun getUserInfo(): BaseResponse<Data> =
        launchIO { ApiManager.biliService.loadUserInfo().await() }

}