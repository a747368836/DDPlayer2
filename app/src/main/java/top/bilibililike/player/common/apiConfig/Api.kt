package top.bilibililike.player.common.apiConfig


import top.bilibililike.player.common.dao.userDataBase
import java.util.concurrent.ConcurrentHashMap



object Api {
    const val DOMAIN_HEADER = "domainn"
    const val BILI_HEADER = "bili"
    const val LOGIN_HOST = "https://passport.bilibili.com"
    const val APP_HOST = "https://app.bilibili.com"
    const val API_HOST = "https://api.bilibili.com"
    const val BILIPLUS_HOST = "https://www.biliplus.com"
    const val appsecret = "560c52ccd288fed045859ed18bffd973"
    const val videosecret = "aHRmhWMLkdeMuILqORnYZocwMBpMEOdt"
    const val videoAppkey = "iVGUTjsxvpLeuDCf"
    const val appkey = "1d8b6e7d45233436"
    const val build = "5410000"
    const val channel = "bili"
    const val mobi_app = "android"
    const val platform = "android"
    const val statistics = "{\"appId\":1,\"platform\":3,\"version\":\"5.41.0\",\"abtest\":\"\"}"
    private var params: ConcurrentHashMap<String, String>? = null
    fun initParams() {

        val tokenInfo = userDataBase.getTokenDao().getTokenAsyc()


        if (params != null) {
            params!!.clear()
        } else {
            params = ConcurrentHashMap()
        }
        params!!["appkey"] = appkey
        if (tokenInfo != null) {
            params!!["access_key"] = tokenInfo.access_token
        }
        params!!["build"] = build
        params!!["channel"] = channel
        params!!["mobi_app"] = mobi_app
        params!!["platform"] = platform
        params!!["statistics"] = statistics
        params!!["ts"] = System.currentTimeMillis().toString().substring(0, 10)
    }

    fun getParams(): ConcurrentHashMap<String, String> {
        initParams()
        return params!!
    }

}
