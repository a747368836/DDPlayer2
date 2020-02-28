package top.bilibililike.player.common.bean.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import top.bilibililike.player.common.bean.userInfo.*

/**
 *  Project: DDPlayer
 *  Created by Xbs on 2020/2/29
 *  Describe:
 */

@Dao
interface UserInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveToken(vararg element:Data)

    @Query("SELECT * from USERINFO_CLASS LIMIT 1")
    suspend fun getUserObject():Data

    @Query("SELECT * from USERINFO_CLASS LIMIT 1")
    fun getUserObjectAsyc():Data

    @Query("SELECT name from USERINFO_CLASS LIMIT 1")
    suspend fun getNickName():String

    @Query("SELECT face from USERINFO_CLASS LIMIT 1")
    suspend fun getAvatar():String

    @Query("SELECT mid from USERINFO_CLASS LIMIT 1")
    suspend fun getMid():Int
}