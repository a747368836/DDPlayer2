package top.bilibililike.player.widget.search

import top.bilibililike.mvp.mvp.BaseModel
import top.bilibililike.player.common.bean.BaseResponse
import top.bilibililike.player.common.bean.search.SearchResultBean
import top.bilibililike.player.common.http.ApiManager


object SearchModel : BaseModel() {
    suspend fun getSearchBean(keyword: String,page:Int,num:Int): BaseResponse<SearchResultBean.DataBean> =
        launchIO { ApiManager.biliService.getSearchResult(keyword,page,num).await() }

}