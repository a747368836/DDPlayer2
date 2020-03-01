package top.bilibililike.player.widget.search

import top.bilibililike.mvp.mvp.BasePresenter


class SearchPresenter(view: SearchContract.View) : BasePresenter<SearchContract.View>(view), SearchContract.Presenter {
    var counter = 0;
    override fun getSearchResult(keyword: String) {
        launchUI({
            val response = SearchModel.getSearchBean(keyword)
            if (response.isSuccess()){
                view?.getSearchSuccess(response.getRawData()!!)
            }else if (response.getCode() == -3){
                counter++;
                if (counter < 5){
                    getSearchResult(keyword)
                }

            }
            counter = 0;
        })
    }

}