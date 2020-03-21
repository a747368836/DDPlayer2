package top.bilibililike.subtitle.subtitle

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.view.WindowManager

/**
 * @author Xbs
 */
class ConfigurationReceiver : BroadcastReceiver() {
    lateinit var listener: ConfigurationChangedListener

    override fun onReceive(context: Context, intent: Intent) {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        listener.configurationChanged(windowManager.defaultDisplay.rotation * 90)
    }

    fun bindListener(listener: ConfigurationChangedListener) {
        this.listener = listener
    }


}

/**
 * @author Xbs
 * @date 2020年1月15日17:14:38
 */
interface ConfigurationChangedListener {
    /**
     * 屏幕转动回调
     * @param angle 转动角度
     */
    fun configurationChanged(angle: Int)
}