package top.bilibililike.player.common.bean.login.token

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "token_class")
data class TokenInfo(

    @ColumnInfo(name = "access_token")
    val access_token: String,

    @ColumnInfo(name = "expires_in")
    val expires_in: Int,

    @PrimaryKey
    @ColumnInfo(name = "mid")
    val mid: Int,

    @ColumnInfo(name = "refresh_token")
    val refresh_token: String
)