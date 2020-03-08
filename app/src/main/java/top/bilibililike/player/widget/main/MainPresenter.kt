package top.bilibililike.player.widget.main

import top.bilibililike.mvp.mvp.BasePresenter
import top.bilibililike.player.common.dao.userDataBase


/**
 *  @author: Xbs
 *  @date:   2020/02/29
 *  @desc:   TODO
 */

class MainPresenter(view: MainContract.View) : BasePresenter<MainContract.View>(view),
    MainContract.Presenter {


    override fun loadUserInfo() {
        launchUI({
            val cache = MainModel.getCacheInfo()
            if (cache != null) {
                view?.showUserInfo(cache)
            }
            val response = MainModel.getUserInfo()
            if (response.isSuccess()) {
                view?.showUserInfo(response.getRawData()!!)
                userDataBase.getUserInfoDao().saveToken(response.getRawData()!!)
            }

        })
    }

}