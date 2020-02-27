package top.bilibililike.mvp.mvp
import android.content.Context
import androidx.annotation.StringRes
import top.bilibililike.mvp.base.BaseFragment
import top.bilibililike.mvp.ext.Toasts.toast

abstract class MVPFragment<P : BaseContract.Presenter>: BaseFragment(), BaseContract.View {

    lateinit var mPresenter: P

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mPresenter = bindPresenter()
    }

    abstract fun bindPresenter(): P

    override fun showError(@StringRes msgRes: Int) {
        showError(getString(msgRes))
    }

    override fun showError(msg: String) {
        context?.toast(msg)
        hideLoading()
    }

    override fun showLoading() {}

    override fun hideLoading() {}

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}