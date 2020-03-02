package top.bilibililike.player.widget.player

import top.bilibililike.mvp.constant.Const
import top.bilibililike.mvp.mvp.BasePresenter

/**
 *  @author: Xbs
 *  @date:   2020/02/29
 *  @desc:   
 */

class LivePlayerPresenter(view: LivePlayerContract.View) : BasePresenter<LivePlayerContract.View>(view),
    LivePlayerContract.Presenter {
    var retryCounter = 0;
    override fun getVideoDetail(av: String) {
        view?.showLoading()
        launchUI({
            val response = LivePlayerModel.getVideoDetail(av)
            if (response.isSuccess()){
                view?.getVideoDetailSuccess(response.getRawData()!!)
                retryCounter = 0
                return@launchUI
            }else if (response.getCode() == -3){
                //密钥又算错了，重试
                getVideoDetail(av).takeIf { ++retryCounter < 10 }
                //别问，问就是网不行
                view?.showError(Const.NETWORK_ERROR)
            }
        })
    }

    override fun getAvPlayUrl(av: String,cid:String, qn: String) {
        launchUI({
            val response =
                LivePlayerModel.getVideoUrl(av, cid, qn)
            if (response.isSuccess()){
                view?.getVideoUrlSuccess(response.getRawData()!!)
                view?.hideLoading()
                return@launchUI
            }else if (response.getCode() == -3){
                //密钥又算错了，重试
                getAvPlayUrl(av,cid,qn).takeIf { ++retryCounter < 10 }
                //别问，问就是网不行
                view?.showError(Const.NETWORK_ERROR)
            }

        })
    }

    override fun getBangumiPlayUrl() {

    }

    override fun getBangumiOverSeaUrl() {

    }

    override fun getLiveUrl(roomId: String) {
        launchUI({
            val response = LivePlayerModel.getLiveUrl(roomId)
            if (response.isSuccess()){
                view?.getLiveUrlSuccess(response.getRawData()!!)
            }
        })
    }

}