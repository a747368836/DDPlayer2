package top.bilibililike.player.common


import android.util.Log
import okhttp3.HttpUrl

import java.io.IOException
import java.net.URLEncoder
import java.util.ArrayList
import java.util.Collections
import java.util.HashMap

import okhttp3.Interceptor
import okhttp3.Response
import top.bilibililike.player.common.api.Api
import top.bilibililike.player.common.sign.Signer


/**
 * @author Xbs
 * @date 2020年2月28日
 * B站api参数填充器，会自动填充统计参数并补充url签名（GET的）
 * it will fill in the params that need when linking www.bilibili.com
 */
class BiliInterceptor : Interceptor {
    private var paramMap: HashMap<String, String>? = null

    private constructor() {

    }

    constructor(paramMap: HashMap<String, String>?) {
        if (paramMap != null) {
            this.paramMap = paramMap
        }
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()
        //对是GET模式 且 header 中 domain 对应的内容 含有bili 的请求进行拦截
        if (!request.method().contains("GET")) {
            return chain.proceed(request)
        }else{
            val headerStr = request.header(Api.DOMAIN_HEADER)
            if(headerStr != null && !headerStr.contains(Api.BILI_HEADER)){
                return chain.proceed(request)
            }
        }

        //获取统计参数 并重组url + 签名
        val paramMap = HashMap(Api.getParams())
        if (this.paramMap != null) {
            paramMap.putAll(this.paramMap!!)
        }

        var httpUrl = request.url()

        httpUrl.queryParameterNames()
        val queryNames = ArrayList(httpUrl.queryParameterNames())
        for (param in queryNames) {
            Log.d("GetInterceptor", param)
            paramMap[param] = httpUrl.queryParameterValues(param)[0]
        }
        val paramNames = ArrayList(paramMap.keys)
        Collections.sort(paramNames)

        //保留原url有的host port 和路径segment、scheme（http/https）
        var builder = HttpUrl.Builder()
        builder = builder.scheme(httpUrl.scheme()).host(httpUrl.host())
        for(segment in httpUrl.pathSegments()){
            builder = builder.addPathSegment(segment)
        }

        //签名文本拼接
        val signBuilder = StringBuilder()
        for (i in paramNames.indices) {
            builder.addQueryParameter(paramNames[i], paramMap[paramNames[i]])
            signBuilder.append(paramNames[i]).append("=")
                .append(URLEncoder.encode(paramMap[paramNames[i]]))
            if (i + 1 < paramNames.size) {
                signBuilder.append("&")
            }
        }
        signBuilder.append(Api.appsecret)
        builder.addQueryParameter("sign", Signer.getSign(signBuilder.toString()))
        httpUrl = builder.build()
        request = request.newBuilder().url(httpUrl).build()
        return chain.proceed(request)

    }

    @Deprecated("useless")
    fun replaceParam(param: HashMap<String, String>?) {
        if (paramMap == null) {
            paramMap = HashMap()
        } else {
            paramMap!!.clear()
            paramMap!!.putAll(Api.getParams())
        }
        if (param != null) {
            paramMap!!.putAll(param)
        }
    }

}
