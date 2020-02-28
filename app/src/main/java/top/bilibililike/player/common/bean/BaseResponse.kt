package top.bilibililike.player.common.bean

import top.bilibililike.mvp.bean.Response
import top.bilibililike.mvp.constant.Const

/**
 * it can be applied to Restful interfaces
 */

class BaseResponse<T> : Response<T> {
    private var code: Int = 0
    private var message: String = Const.MESSAGE_EMPTY
    private var ttl: Int = 0
    private var data: T? = null

    override fun isSuccess(): Boolean = code == 0

    override fun getMessage(): String = message

    override fun getRawData(): T? = data
}
