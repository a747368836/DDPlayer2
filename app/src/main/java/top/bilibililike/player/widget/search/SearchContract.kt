package top.bilibililike.player.widget.search

import top.bilibililike.mvp.mvp.BaseContract
import top.bilibililike.player.common.bean.search.SearchResultBean


interface SearchContract {

    interface View : BaseContract.View{
        fun getSearchSuccess(searchBean:SearchResultBean.DataBean)
    }

    interface Presenter : BaseContract.Presenter {
        fun getSearchResult(keyword:String)

    }
}
