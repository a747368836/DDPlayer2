package top.bilibililike.player.widget.player

import top.bilibililike.mvp.mvp.BasePresenter

/**
 *  @author: Xbs
 *  @date:   2020/02/29
 *  @desc:   
 */

class PlayerPresenter(view: PlayerContract.View) : BasePresenter<PlayerContract.View>(view),
    PlayerContract.Presenter {
    override fun getVideoDetail(av: String) {
        launchUI({
            val response = PlayerModel.getVideoDetail(av)
            if (response.isSuccess()){
                view?.getVideoDetailSuccess(response.getRawData()!!)
            }
        })
    }

    override fun getAvPlayUrl(av: String,cid:String, qn: String) {
        launchUI({
            val response =
                PlayerModel.getVideoUrl(av, cid, qn)
            if (response.isSuccess()){
                view?.getVideoUrlSuccess(response.getRawData()!!)
            }
        })
    }

    override fun getBangumiPlayUrl() {

    }

    override fun getBangumiOverSeaUrl() {

    }

    override fun getLiveUrl(roomId: String) {

    }

}