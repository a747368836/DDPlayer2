package top.bilibililike.player.common.bean.dao

import androidx.room.*
import kotlinx.coroutines.Deferred
import top.bilibililike.player.common.bean.login.token.TokenInfo
import top.bilibililike.player.common.bean.userInfo.Data


/**
 *  Project: DDPlayer
 *  Created by Xbs on 2020/2/29
 *  Describe:
 */
@Dao
interface TokenDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveToken(vararg element: TokenInfo)

    /*@Update
    suspend fun updateToken(element: TokenInfo)*/

    @Query("SELECT * from TOKEN_CLASS LIMIT 1 ")
    suspend fun getToken(): TokenInfo?

    @Query("SELECT * from TOKEN_CLASS LIMIT 1 ")
    fun getTokenAsyc(): TokenInfo?

    @Query("SELECT mid from TOKEN_CLASS LIMIT 1 ")
    suspend fun getUserMid(): Int?
}



