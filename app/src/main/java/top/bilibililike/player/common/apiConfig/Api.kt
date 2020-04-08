package top.bilibililike.player.common.apiConfig


import top.bilibililike.player.common.MyApp
import top.bilibililike.player.common.dao.userDataBase
import java.util.concurrent.ConcurrentHashMap


object Api {
    const val DOMAIN_HEADER = "domainn"
    const val BILI_HEADER = "bili"
    const val LOGIN_HOST = "https://passport.bilibili.com"
    const val APP_HOST = "https://app.bilibili.com"
    const val API_HOST = "https://api.bilibili.com"
    const val BILIPLUS_HOST = "https://www.biliplus.com"
    const val APP_SECRET = "560c52ccd288fed045859ed18bffd973"
    const val VIDEO_SECRET = "aHRmhWMLkdeMuILqORnYZocwMBpMEOdt"
    const val VIDEO_APPKEY = "iVGUTjsxvpLeuDCf"
    const val appkey = "1d8b6e7d45233436"
    const val build = "5410000"
    const val channel = "bili"
    const val mobi_app = "android"
    const val platform = "android"
    const val device = "android"
    const val statistics = "{\"appId\":1,\"platform\":3,\"version\":\"5.41.0\",\"abtest\":\"\"}"
    const val buvid = "XZ3B2B93A770D6969B54AB127B42DB2031888"

    private var params: ConcurrentHashMap<String, String>? = null
    fun initParams() {

        val accessToken :String = MyApp.userToken
        if (params != null) {
            params!!.clear()
        } else {
            params = ConcurrentHashMap()
        }
        params!!["appkey"] = appkey
        if (MyApp.hasLogin) {
            params!!["access_key"] = accessToken
        }
        params!!["build"] = build
        params!!["channel"] = channel
        params!!["device"] = device
        params!!["mobi_app"] = mobi_app
        params!!["platform"] = platform
        params!!["statistics"] = statistics
        params!!["ts"] = System.currentTimeMillis().toString().substring(0, 10)
    }

    fun initVideoParams() {
        val tokenInfo = userDataBase.getTokenDao().getTokenAsyc()
        if (params != null) {
            params!!.clear()
        } else {
            params = ConcurrentHashMap()
        }
        //又长又臭 还不能省略。。。
        params!!["actionkey"] = "appkey"
        params!!["appkey"] = VIDEO_APPKEY
        params!!["build"] = build
        params!!["buvid"] = buvid
        params!!["device"] = device
        params!!["expire"] = "1567060310"
        params!!["fnval"] = "16"
        params!!["fnver"] = "0"
        params!!["force_host"] = "0"
        params!!["fourk"] = "0"
        params!!["from_spmid"] = "tm.recommend.0.0"
        params!!["mobi_app"] = mobi_app
        params!!["npcybs"] = "0"
        params!!["otype"] = "json"
        params!!["platform"] = platform
        params!!["spmid"] = "main.ugc-video-detail.0.0"
        params!!["ts"] = System.currentTimeMillis().toString().substring(0, 10)
    }

    fun getParams(): ConcurrentHashMap<String, String> {
        initParams()
        return params!!
    }

    fun getVideoParams(): ConcurrentHashMap<String, String> {
        initVideoParams()
        return params!!
    }

}
