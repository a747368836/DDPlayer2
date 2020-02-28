package top.bilibililike.mvp.mvp

import androidx.annotation.StringRes



interface BaseContract {

    interface View {
        fun showError(@StringRes msgRes: Int)

        fun showError(msg: String)

        fun showLoading()

        fun hideLoading()
    }

    interface Presenter {
        fun showError(msg: String)

        fun showError(@StringRes strRes: Int)

        fun showLoading()

        fun detachView()
    }
}