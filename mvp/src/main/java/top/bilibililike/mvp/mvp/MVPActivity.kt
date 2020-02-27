package top.bilibililike.mvp.mvp


import androidx.annotation.StringRes
import kotlinx.android.synthetic.main.k_layout_empty.*
import top.bilibililike.mvp.base.ToolbarActivity
import top.bilibililike.mvp.ext.Toasts.toast
import top.bilibililike.mvp.widget.LoadingDialog


abstract class MVPActivity<P : BaseContract.Presenter> : ToolbarActivity(), BaseContract.View {

    val loadingDialog by lazy { LoadingDialog.create(supportFragmentManager) }

    lateinit var presenter: P

    override fun initBefore() {
        presenter = bindPresenter()
    }

    abstract fun bindPresenter(): P

    override fun showError(@StringRes msgRes: Int) {
        showError(getString(msgRes))
    }

    override fun showError(msg: String) {
        toast(msg)
        hideLoading()
    }

    override fun showLoading() {
        loadingDialog.show()
    }

    override fun hideLoading() {
        loadingDialog.hide()
    }

    override fun onDestroy() {
        super.onDestroy()

        if (::presenter.isInitialized) {
            presenter.detachView()
        }
    }
}