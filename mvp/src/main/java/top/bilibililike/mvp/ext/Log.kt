package top.bilibililike.mvp.ext

import android.util.Log
import top.bilibililike.mvp.constant.Const


fun logv(message: String, tag: String = Const.PROJECT) = Log.v(tag, message)

fun logd(message: String, tag: String = Const.PROJECT) = Log.d(tag, message)

fun logi(message: String, tag: String = Const.PROJECT) = Log.i(tag, message)

fun logw(message: String, tag: String = Const.PROJECT) = Log.w(tag, message)

fun loge(message: String, tag: String = Const.PROJECT) = Log.e(tag, message)

fun String.showLog() {
    Log.d(Const.PROJECT, "<-------------------${Const.PROJECT} Start--------------------")
    Log.d(Const.PROJECT, "[content]:  $this")
    Log.d(Const.PROJECT, "--------------------${Const.PROJECT} End------------------->")
}


