package top.bilibililike.player.widget.main

import top.bilibililike.mvp.mvp.BaseContract
import top.bilibililike.player.common.bean.userInfo.Data


/**
 *  @author: Xbs
 *  @date:   2020/02/29
 *  @desc:   TODO
 */

interface MainContract {

    interface View : BaseContract.View{
        fun showUserInfo(dataBean:Data?)
    }

    interface Presenter : BaseContract.Presenter {
        fun loadUserInfo()
    }
}
