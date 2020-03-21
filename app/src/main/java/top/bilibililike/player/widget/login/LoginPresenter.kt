package top.bilibililike.player.widget.login

import android.util.Log
import top.bilibililike.mvp.mvp.BasePresenter


/**
 *  @author: Xbs
 *  @date:   2020/02/28
 *  @desc:
 */

class LoginPresenter(view: LoginContract.View) : BasePresenter<LoginContract.View>(view),
    LoginContract.Presenter {

    override fun login(username: String, password: String) {
        launchUI({
            showLoading()
            val keyResponse = LoginModel.getRsaKey()
            if (!keyResponse.isSuccess()) {
                view?.loginFailed(keyResponse.getMessage())
            } else {
                val tokenRes = LoginModel.getToken(
                    username,
                    password,
                    keyResponse.getRawData()!!,
                    keyResponse.getTs()!!
                )
                if (tokenRes.isSuccess() && tokenRes.getRawData()?.status == 0) {
                    view?.loginSuccess(tokenRes.getRawData()!!)
                    LoginModel.saveTokenData(tokenRes.getRawData()?.token_info!!)
                } else {
                    Log.d("LoginPresenter","tokenRes.isSuccess() = ${tokenRes.isSuccess()}")
                    view?.loginFailed(tokenRes.getMessage())
                }

            }
        })
    }



}