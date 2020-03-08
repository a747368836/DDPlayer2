package top.bilibililike.mvp.mvp


import androidx.annotation.StringRes
import kotlinx.coroutines.*
import top.bilibililike.mvp.bean.Response
import top.bilibililike.mvp.constant.Const
import top.bilibililike.mvp.ext.showLog
import top.bilibililike.mvp.ext.tryCatch
import java.lang.ref.WeakReference


abstract class BasePresenter<V : BaseContract.View>(view: V) : BaseContract.Presenter {

    val view: V?
        get() = mViewRef.get()

    // View 接口类型的弱引用
    private var mViewRef = WeakReference(view)

    val presenterScope: CoroutineScope by lazy {
        CoroutineScope(Dispatchers.Main + Job())
    }

    fun launchUI(block: suspend CoroutineScope.() -> Unit, error: ((Throwable) -> Unit)? = null) {
        presenterScope.launch {
            tryCatch({
                block()
            }, {
                error?.invoke(it) ?: showException(it.toString())
            })
        }
    }

    fun <R> quickLaunch(block: Execute<R>.() -> Unit) {
        Execute<R>().apply(block)
    }

    private fun showException(exception: String) {
        exception.showLog()
        view?.showError(Const.NETWORK_ERROR)
    }


    fun <R> Response<R>.execute(success: ((R?) -> Unit)?, error: ((String) -> Unit)? = null) {
        if (this.isSuccess()) {
            success?.invoke(this.getRawData())
            return
        }

        error?.invoke(this.getMessage()) ?: showError(this.getMessage())
    }

    override fun showError(msg: String) {
        view?.showError(msg)
    }

    override fun showError(@StringRes strRes: Int) {
        view?.showError(strRes)
    }

    override fun showLoading() {
        view?.showLoading()
    }

    override fun detachView() {
        mViewRef.clear()
        // 取消掉 presenterScope创建的所有协程和其子协程。
        presenterScope.cancel()
    }


    inner class Execute<R> {

        private var successBlock: ((R?) -> Unit)? = null
        private var failBlock: ((String?) -> Unit)? = null
        private var exceptionBlock: ((Throwable?) -> Unit)? = null

        fun request(block: suspend CoroutineScope.() -> Response<R>?) {
            launchUI({ block()?.execute(successBlock, failBlock) }, exceptionBlock)
        }

        fun requestCache(block: suspend CoroutineScope.() -> Response<R>?) {
            launchUI({ block()?.execute(successBlock, failBlock) }, exceptionBlock)
        }

        fun onSuccess(block: (R?) -> Unit) {
            this.successBlock = block
        }

        fun onFail(block: (String?) -> Unit) {
            this.failBlock = block
        }

        fun onException(block: (Throwable?) -> Unit) {
            this.exceptionBlock = block
        }
    }
}