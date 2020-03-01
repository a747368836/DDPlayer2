package top.bilibililike.player.widget.login

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_login.*
import top.bilibililike.mvp.constant.Const
import top.bilibililike.mvp.ext.Toasts.toast
import top.bilibililike.mvp.mvp.MVPActivity
import top.bilibililike.player.R
import top.bilibililike.player.common.bean.login.token.Data
import top.bilibililike.player.widget.main.MainActivity


/**
 *  @author: Xbs
 *  @date:   2020/02/28
 *  @desc:
 */
/**
 * MVP  M = Model  负责数据加载
 *      V - View   负责数据的呈现 一般都是activity/fragment
 *      P = Presenter 负责数据的调度  Model 和 View 的桥梁
 */
class LoginActivity : MVPActivity<LoginContract.Presenter>(), LoginContract.View {
    override fun loginSuccess(data: Data) {
        hideLoading()
        val intent = Intent(this, MainActivity::class.java)
        intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Const.INTENT_EVENT, Const.EVENT_LOGIN);
        startActivity(intent)
        toast(Const.LOGIN_SUCCESS)
    }

    override fun loginFailed(reason: String) {
        hideLoading()
        toast(reason)
    }

    override fun initView() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        mBtnLogin.setOnClickListener {
            presenter.login(mEtAccount.text.toString(), mEtPassword.text.toString())
        }
        //输入密码闭眼的彩蛋
        mEtPassword.setOnFocusChangeListener { view, hasFocus ->
            run {
                if (hasFocus) {
                    img_22.setImageDrawable(resources.getDrawable(R.mipmap.ic_22_hide))
                    img_33.setImageDrawable(resources.getDrawable(R.mipmap.ic_33_hide))
                } else {
                    img_22.setImageDrawable(resources.getDrawable(R.mipmap.ic_22))
                    img_33.setImageDrawable(resources.getDrawable(R.mipmap.ic_33))
                }
            }
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_login

    override fun bindPresenter(): LoginContract.Presenter = LoginPresenter(this)


}