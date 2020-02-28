package top.bilibililike.player.widget.login

import top.bilibililike.mvp.mvp.MVPActivity
import top.bilibililike.player.R

/**
 *  @author: Xbs
 *  @date:   2020/02/28
 *  @desc:   
 */

class LoginActivity : MVPActivity<LoginContract.Presenter>(), LoginContract.View {

    override fun getLayoutId(): Int = R.layout.activity_login

    override fun bindPresenter(): LoginContract.Presenter = LoginPresenter(this)
}