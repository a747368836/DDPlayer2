package top.bilibililike.player.widget.recommend

import top.bilibililike.mvp.mvp.BaseContract
import top.bilibililike.player.common.bean.recommend.Data


interface RecommendContract {

    interface View : BaseContract.View{


        fun loadMoreListSuccess(response:Data)

        fun refreshListSuccess(response:Data)

        fun refreshListFailed()
    }

    interface Presenter : BaseContract.Presenter {
        fun loadRecommendList(isRefresh:Boolean)
    }
}
