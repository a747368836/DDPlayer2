package top.bilibililike.player.widget.recommend

import top.bilibililike.mvp.mvp.BasePresenter
import top.bilibililike.player.common.bean.recommend.Data


class RecommendPresenter(view: RecommendContract.View) :
    BasePresenter<RecommendContract.View>(view), RecommendContract.Presenter {
    override fun loadRecommendList(isRefresh: Boolean) {
        launchUI({
            view?.showLoading()
            val response = RecommendModel.loadRecommendList(isRefresh)
            view?.hideLoading()
            response.takeIf { response.isSuccess() }?.apply {
                if (isRefresh) {
                    view?.refreshListSuccess(getRawData()!!)
                } else {
                    view?.loadMoreListSuccess(getRawData()!!)
                }
            }
        }
        )
    }


}