package top.bilibililike.player.common.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import top.bilibililike.player.common.bean.login.token.TokenInfo
import top.bilibililike.player.common.bean.userInfo.Data

/**
 *  Project: DDPlayer
 *  Created by Xbs on 2020/2/29
 *  Describe:
 */
@Database(entities = [TokenInfo::class, Data::class], version = 1)
abstract class UserDataBase : RoomDatabase() {
    abstract fun getTokenDao(): TokenDao

    abstract fun getUserInfoDao(): UserInfoDao
}
