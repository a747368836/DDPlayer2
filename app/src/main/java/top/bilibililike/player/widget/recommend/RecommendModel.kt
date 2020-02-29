package top.bilibililike.player.widget.recommend

import top.bilibililike.mvp.mvp.BaseModel
import top.bilibililike.player.common.bean.BaseResponse
import top.bilibililike.player.common.bean.recommend.Data

import top.bilibililike.player.common.http.ApiManager


object RecommendModel: BaseModel(){
    suspend fun loadRecommendList(isRefresh:Boolean) : BaseResponse<Data> {
        return launchIO { ApiManager.biliService.getRecommendList(isRefresh).await() }
    }
}