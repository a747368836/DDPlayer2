package top.bilibililike.player.widget.recommend

import top.bilibililike.mvp.mvp.BaseContract
import top.bilibililike.player.common.bean.recommend.Data


interface RecommendContract {

    interface View : BaseContract.View{
        fun loadRecommendListSuccess(response:Data)

        fun loadMoreListSuccess(response:Data)

        fun refreshListSuccess(response:Data)
    }

    interface Presenter : BaseContract.Presenter {
        fun loadRecommendList(isRefresh:Boolean)

        fun loadMoreList()
    }
}
