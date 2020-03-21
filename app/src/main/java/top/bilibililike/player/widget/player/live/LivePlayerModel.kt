package top.bilibililike.player.widget.player

import top.bilibililike.mvp.mvp.BaseModel
import top.bilibililike.player.common.bean.BaseResponse
import top.bilibililike.player.common.bean.avDescription.AvDescriptionBean
import top.bilibililike.player.common.bean.avUrl.Data
import top.bilibililike.player.common.bean.live.LivePlayUrlBean
import top.bilibililike.player.common.http.ApiManager


/**
 *  @author: Xbs
 *  @date:   2020/02/29
 *  @desc:
 */

object LivePlayerModel : BaseModel() {
    suspend fun getVideoUrl(av: String, cid: String, qn: String): BaseResponse<Data> =
        launchIO {
            ApiManager.biliService.getVideoUrl(av, cid, qn).await()
        }

    suspend fun getVideoDetail(av: String): BaseResponse<AvDescriptionBean.DataBean> =
        launchIO {
            ApiManager.biliService.getVideoDetail(av).await()
        }

    suspend fun getLiveUrl(roomId: String): BaseResponse<LivePlayUrlBean.DataBean> =
        launchIO {
            ApiManager.biliService.getLivePlayUrl(roomId).await()
        }


}