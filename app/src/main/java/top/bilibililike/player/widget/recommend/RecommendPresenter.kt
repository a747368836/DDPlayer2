package top.bilibililike.player.widget.recommend

import top.bilibililike.mvp.mvp.BasePresenter
import top.bilibililike.player.common.bean.recommend.Data



class RecommendPresenter(view: RecommendContract.View) : BasePresenter<RecommendContract.View>(view), RecommendContract.Presenter {
    override fun loadMoreList() {
        launchUI({
            view?.showLoading()
            val response = RecommendModel.loadRecommendList(false)
            view?.hideLoading()
            if (response.isSuccess()){
                view?.loadMoreListSuccess(response.getRawData() as Data)
            }
        }
        )
    }

    override fun loadRecommendList(isRefresh:Boolean){
        launchUI({
            view?.showLoading()
            val response = RecommendModel.loadRecommendList(isRefresh)
            view?.hideLoading()
            if (response.isSuccess()){
                view?.loadRecommendListSuccess(response.getRawData() as Data)
            }
        }
        )

        /*quickLaunch<Data> {
            request { RecommendModel.loadRecommendList(true) }

            onSuccess { loginRsp ->
                loginRsp?.let { view?.loadRecommendListSuccess(loginRsp) }
            }

            onFail { message ->
                message?.let { view?.showError(message) }
            }

            onException { throwable ->
                throwable?.let { view?.showError(throwable.toString()) }
            }
        }*/
    }


}