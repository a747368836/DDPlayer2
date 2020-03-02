package top.bilibililike.player.widget.player

import top.bilibililike.mvp.mvp.BaseContract
import top.bilibililike.player.common.bean.avDescription.AvDescriptionBean
import top.bilibililike.player.common.bean.avUrl.Data
import top.bilibililike.player.common.bean.live.LivePlayUrlBean


/**
 *  @author: Xbs
 *  @date:   2020/02/29
 *  @desc:   
 */

interface LivePlayerContract {

    interface View : BaseContract.View{
        fun getVideoUrlSuccess(urlDataBean:Data)

        fun getLiveUrlSuccess(liveUrlBean:LivePlayUrlBean.DataBean)
        
        fun getVideoDetailSuccess(dataBean:AvDescriptionBean.DataBean)
    }

    interface Presenter : BaseContract.Presenter {
        fun getAvPlayUrl(av:String,cid:String,qn:String)

        fun getVideoDetail(av:String)

        fun getBangumiPlayUrl()

        fun getBangumiOverSeaUrl()

        fun getLiveUrl(roomId:String)

        //todo 电影暂不清楚是啥 就那种ep的不是av的
    }
}
