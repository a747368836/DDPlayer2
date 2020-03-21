package top.bilibililike.player.common.bean.login.token

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "token_class")
data class TokenInfo(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id : Int = 1,

    @ColumnInfo(name = "access_token")
    val access_token: String,

    @ColumnInfo(name = "expires_in")
    val expires_in: Int,

    @ColumnInfo(name = "mid")
    val mid: Int,

    @ColumnInfo(name = "refresh_token")
    val refresh_token: String
){
    constructor(): this (1,"",0,0,"")
}