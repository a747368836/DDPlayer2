package top.bilibililike.player.common.bean.userInfo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "userInfo_class")
data class Data(
    @PrimaryKey
    @ColumnInfo(name = "mid")
    val mid: Int,

    @ColumnInfo(name = "level")
    val level: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "sign")
    val sign: String,
    @ColumnInfo(name = "face")
    val face: String,
    @ColumnInfo(name = "coins")
    val coins: Double

   /* @Ignore
    val birthday: String,

    @Ignore
    val email_status: Int,

    @Ignore
    val identification: Int,
    @Ignore
    val invite: Invite,
    @Ignore
    val is_tourist: Int,
    @Ignore
    val official: Official,
    @Ignore
    val pendant: Pendant,
    @Ignore
    val pin_prompting: Int,
    @Ignore
    val rank: Int,
    @Ignore
    val sex: Int,
    @Ignore
    val silence: Int,
    @Ignore
    val tel_status: Int,
    @Ignore
    val vip: Vip*/
)