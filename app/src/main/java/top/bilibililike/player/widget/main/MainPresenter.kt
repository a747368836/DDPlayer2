package top.bilibililike.player.widget.main

import top.bilibililike.mvp.mvp.BasePresenter
import top.bilibililike.player.common.bean.userInfo.Data


/**
 *  @author: Xbs
 *  @date:   2020/02/29
 *  @desc:   TODO
 */

class MainPresenter(view: MainContract.View) : BasePresenter<MainContract.View>(view), MainContract.Presenter {
    override fun loadUserInfo() {
        if(MainModel.getCacheInfo() != null){
            view?.showUserInfo(MainModel.getCacheInfo()!!)
        }
        launchUI({
            val response = MainModel.getUserInfo()
            if (response.isSuccess()){
                view?.showUserInfo(response.getRawData()!!)
            }

        })
    }

}