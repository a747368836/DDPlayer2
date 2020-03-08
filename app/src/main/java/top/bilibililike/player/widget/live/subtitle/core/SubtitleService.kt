package top.bilibililike.subtitle.subtitle

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.PixelFormat
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import java.util.concurrent.Executors
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import top.bilibililike.player.R
import top.bilibililike.player.widget.live.subtitle.core.SubtitleView
import top.bilibililike.player.widget.live.subtitle.core.WebSocket.DanmakuCallBack
import top.bilibililike.subtitle.subtitle.WebSocket.SocketDataThread

/**
 * @author Xbs
 */
class SubtitleService : Service(), DanmakuCallBack, ConfigurationChangedListener {
    private val executorService = Executors.newFixedThreadPool(1)
    private lateinit var subtitleView: SubtitleView
    private lateinit var windowManager: WindowManager
    private var dataThread: SocketDataThread? = null


    override fun onBind(intent: Intent): IBinder? {

        val filter = IntentFilter("android.intent.action.CONFIGURATION_CHANGED")
        val receiver = ConfigurationReceiver()
        registerReceiver(receiver, filter)
        receiver.bindListener(this)
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        subtitleView = LayoutInflater.from(this).inflate(R.layout.layout_subtitle_view, null, false) as SubtitleView

        return LocalBinder()
    }

    fun linkStart(roomId: String) {
        dataThread?.stop()
        dataThread = SocketDataThread()
        dataThread?.bind(this)
        dataThread?.start(roomId)
        executorService.execute(dataThread!!)
        updateSubtitleView(0)
    }


    override fun onShow(str: String) {
        Observable.just("0")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<String> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(s: String) {
                        subtitleView.addSubtitle(str)
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {

                    }
                })
    }


    inner class LocalBinder : Binder() {
        val service: SubtitleService
            get() = this@SubtitleService
    }

    override fun configurationChanged(angle: Int) {
        subtitleView.visibility = View.INVISIBLE
        Log.d(TAG, "旋转角度：$angle")
        if (angle == 90 || angle == 270) {
            updateSubtitleView(90)
        } else if (angle == 180 || angle == 0 || angle == 360) {
            updateSubtitleView(0)
        }
        subtitleView.visibility = View.VISIBLE
    }

    private fun updateSubtitleView(gravity: Int) {
        val heightParam: Float
        if (gravity == 0) {
            //竖直
            heightParam = 0.083f
        } else {
            heightParam = 0.148f
        }
        if (dataThread == null) {
            return
        }
        val layoutParams = WindowManager.LayoutParams()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
        }
        //WindowManager.LayoutParams.TYPE_PHONE
        //TYPE_APPLICATION_OVERLAY
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        layoutParams.width = windowManager.defaultDisplay.width
        layoutParams.height = (windowManager.defaultDisplay.height * heightParam).toInt()
        layoutParams.height = (layoutParams.height * 1.5).toInt()
        layoutParams.gravity = Gravity.END
        layoutParams.format = PixelFormat.RGBA_8888
        layoutParams.y = windowManager.defaultDisplay.height - layoutParams.height * 2
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        layoutParams.alpha = 0.8f
        if (subtitleView.isAttachedToWindow) {
            windowManager.updateViewLayout(subtitleView, layoutParams)
        } else {
            windowManager.addView(subtitleView, layoutParams)
        }
    }

    companion object {
        val TAG = "SubtitleService"
    }

}