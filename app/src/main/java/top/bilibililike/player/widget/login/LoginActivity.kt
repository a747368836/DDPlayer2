package top.bilibililike.player.widget.login

import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_login.*
import top.bilibililike.mvp.ext.Toasts.toast
import top.bilibililike.mvp.mvp.MVPActivity
import top.bilibililike.player.R
import top.bilibililike.player.common.bean.login.token.Data


/**
 *  @author: Xbs
 *  @date:   2020/02/28
 *  @desc:   
 */

class LoginActivity : MVPActivity<LoginContract.Presenter>(), LoginContract.View {
    override fun loginSuccess(data: Data) {
        hideLoading()
        toast(data.token_info.mid)
    }

    override fun loginFailed(reason: String) {
        hideLoading()
        toast(reason)
    }

    override fun initView() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        mBtnLogin.setOnClickListener{
            presenter.login(mEtAccount.text.toString(),mEtPassword.text.toString())
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_login

    override fun bindPresenter(): LoginContract.Presenter = LoginPresenter(this)



}