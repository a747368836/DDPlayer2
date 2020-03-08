package top.bilibililike.player.common.dao

import androidx.room.Room
import top.bilibililike.player.common.MyApp

/**
 *  Project: DDPlayer
 *  Created by Xbs on 2020/2/29
 *  Describe:
 */

val userDataBase: UserDataBase by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    Room.databaseBuilder(MyApp.mContext, UserDataBase::class.java, "user").allowMainThreadQueries().build()
}

