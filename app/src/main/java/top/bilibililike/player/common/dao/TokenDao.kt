package top.bilibililike.player.common.dao

import androidx.room.*
import top.bilibililike.player.common.bean.login.token.TokenInfo


/**
 *  Project: DDPlayer
 *  Created by Xbs on 2020/2/29
 *  Describe:
 */
@Dao
interface TokenDao {
    /**
     * 字段 id 为主key  因为api中没有这个字段，但默认值为1  所以直接替换就能保证登录用户唯一
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveToken(vararg element: TokenInfo)

    @Update
    suspend fun updateToken(element: TokenInfo)

    @Query("SELECT * from TOKEN_CLASS LIMIT 1 ")
    suspend fun getToken(): TokenInfo?

    @Query("SELECT * from TOKEN_CLASS LIMIT 1 ")
    fun getTokenAsyc(): TokenInfo?

    @Query("SELECT mid from TOKEN_CLASS LIMIT 1 ")
    suspend fun getUserMid(): Int?
}



