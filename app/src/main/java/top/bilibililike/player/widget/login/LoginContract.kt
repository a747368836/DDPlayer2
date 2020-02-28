package top.bilibililike.player.widget.login

import top.bilibililike.mvp.mvp.BaseContract
import top.bilibililike.player.common.bean.login.token.Data

/**
 *  @author: Xbs
 *  @date:   2020/02/28
 *  @desc:   
 */

interface LoginContract {

    interface View : BaseContract.View{
        fun loginSuccess(data:Data)

        fun loginFailed(reason:String)

    }

    interface Presenter : BaseContract.Presenter {
        fun login(username:String,password:String)
    }
}
