package top.bilibililike.player.common.sign

import java.math.BigInteger
import java.security.MessageDigest


class Signer {
    val APP_SECRET = "560c52ccd288fed045859ed18bffd973"

    fun getSign(content:String) : String{
        return getMD5(content + APP_SECRET)
    }

    /**
     * 对字符串md5加密(小写+字母)
     *
     * @param str 传入要加密的字符串
     * @return  MD5加密后的字符串
     */
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