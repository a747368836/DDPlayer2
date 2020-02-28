package top.bilibililike.player.common.http.interceptors

import java.io.IOException
import java.net.URLEncoder
import java.util.ArrayList
import java.util.Collections

import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Response
import top.bilibililike.player.common.api.Api
import top.bilibililike.player.common.sign.Signer
import top.bilibililike.player.widget.live.subtitle.utils.MD5Util

class BiliPostInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (request.method().contains("POST")) {
            val header: String? = request.header(Api.DOMAIN_HEADER)
            if (header != null) {
                if (header.contains("bili")) {
                    val paramMap = Api.getParams()
                    if (request.body() !is FormBody) {
                        return chain.proceed(request)
                    }
                    var formBody: FormBody = request.body() as FormBody
                    for (i in 0 until formBody.size()) {
                        paramMap[formBody.name(i)] = formBody.value(i)
                    }
                    val paramList = ArrayList(paramMap.keys)
                    Collections.sort(paramList)
                    val bodyBuilder = FormBody.Builder()
                    val signBuilder = StringBuilder()
                    for (i in paramList.indices) {
                        signBuilder.append(paramList[i]).append("=")
                            .append(URLEncoder.encode(paramMap[paramList[i]]))
                        if (i + 1 < paramList.size) {
                            signBuilder.append("&")
                        }
                        bodyBuilder.add(paramList[i], paramMap[paramList[i]]!!)
                    }
                    val signString = signBuilder.toString()
                    bodyBuilder.add("sign",Signer.getSign(signString))
                    formBody = bodyBuilder.build()

                    request = request.newBuilder()
                        .method(request.method(), formBody)
                        .build()
                }
            }
        }

        return chain.proceed(request)
    }
}
