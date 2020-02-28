package top.bilibililike.player.widget.login

import android.util.Base64
import android.util.Log
import androidx.room.Room
import top.bilibililike.mvp.mvp.BaseModel
import top.bilibililike.player.common.MyApp
import top.bilibililike.player.common.bean.BaseResponse
import top.bilibililike.player.common.bean.dao.TokenDataBase
import top.bilibililike.player.common.bean.login.Data
import top.bilibililike.player.common.bean.login.token.TokenInfo
import top.bilibililike.player.common.http.ApiManager
import java.net.URLEncoder
import java.security.KeyFactory
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher
import javax.crypto.Cipher.ENCRYPT_MODE


/**
 *  @author: Xbs
 *  @date:   2020/02/28
 *  @desc:
 */

object LoginModel : BaseModel() {

    suspend fun getRsaKey(): BaseResponse<Data> =
        launchIO { ApiManager.biliService.getRSAKey().await() }

    suspend fun getToken(
        username: String,
        password: String,
        keyBean: Data,
        ts:Int
    ): BaseResponse<top.bilibililike.player.common.bean.login.token.Data> =
        launchIO { ApiManager.biliService.getToken(username, getPassword(password,keyBean),ts).await() }


    private fun getPureRsaKey(publicKey: String): String {
        val result = StringBuilder()
        val keys = publicKey.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (i in keys.indices) {
            keys[i] = keys[i].replace("\n".toRegex(), "")
            if (keys[i][0] != '-') {
                result.append(keys[i])
            }
        }
        return result.toString()
    }

    private fun getPassword(password: String, bean: Data): String {
        //  deal with passWord
        val hash = bean.hash
        val publicKey = getPureRsaKey(bean.key)
        var finalPassword = ""

        try {
            val x509EncodedKeySpec = X509EncodedKeySpec(Base64.decode(publicKey, Base64.DEFAULT))
            val key = KeyFactory.getInstance("RSA")
                .generatePublic(x509EncodedKeySpec)
            val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")

            cipher.init(ENCRYPT_MODE, key)
            finalPassword = Base64.encodeToString(
                cipher.doFinal((hash + password).toByteArray()),
                Base64.DEFAULT
            )
            Log.d("LoginModel encode密码 ", URLEncoder.encode(finalPassword))
        } catch (e: Exception) {
            e.printStackTrace()
        }


        return finalPassword
    }

    suspend fun saveTokenData(tokenBean: TokenInfo){
        launchIO{
            val tokenDao = tokenDataBase.getTokenDao()
            tokenDao.saveToken(tokenBean)
        }
    }

    //mode = LazyThreadSafetyMode.SYNCHRONIZED
    val tokenDataBase: TokenDataBase by lazy() {
        Room.databaseBuilder(MyApp.mContext, TokenDataBase::class.java, "token").allowMainThreadQueries().build()
    }


}