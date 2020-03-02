package top.bilibililike.player.widget.search

import top.bilibililike.mvp.mvp.BasePresenter
import top.bilibililike.player.common.bean.search.SearchResultBean
import java.net.URLEncoder


class SearchPresenter(view: SearchContract.View) : BasePresenter<SearchContract.View>(view), SearchContract.Presenter {
    var counter = 0;
    override fun getSearchResult(keyword: String,page:Int,num:Int,isRefresh:Boolean) {
        if (!isRefresh) showLoading()
        launchUI({
            val response = SearchModel.getSearchBean(keyword,page,num)
            if (response.isSuccess()){
                view?.hideLoading()
                view?.getSearchSuccess(response.getRawData()!!)
            }else if (response.getCode() == -3){
                counter++;
                if (counter < 5){
                    getSearchResult(keyword,page, num,isRefresh)
                }
            }
            view?.hideLoading()
            counter = 0;
        })
    }

}