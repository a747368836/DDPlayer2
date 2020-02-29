package top.bilibililike.player.common.sign


import top.bilibililike.player.common.apiConfig.Api
import java.math.BigInteger
import java.security.MessageDigest


object Signer {
        fun getSign(content:String,isVideo:Boolean = false) : String{
            if (isVideo) return getMD5(content + Api.VIDEO_SECRET)
            return getMD5(content + Api.APP_SECRET)
        }
        private fun getMD5(str: String): String {
            //Log.d("LoginModel md5Str = ",str);
            try {
                // 生成一个MD5加密计算摘要
                val md = MessageDigest.getInstance("MD5")
                // 计算md5函数
                md.update(str.toByteArray())
                // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
                // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
                //  Log.d("LoginModel md5Sign = ",result);
                return BigInteger(1, md.digest()).toString(16)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return ""
        }



}