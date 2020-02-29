package top.bilibililike.player.common.bean.userInfo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "userInfo_class")
data class Data(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id : Int = 1,

    @ColumnInfo(name = "mid")
    var mid: Int,

    @ColumnInfo(name = "level")
    var level: Int,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "sign")
    var sign: String,
    @ColumnInfo(name = "face")
    var face: String,
    @ColumnInfo(name = "coins")
    var coins: Double,

    @Ignore
    val birthday: String,

    @Ignore
    val email_status: Int,

    @Ignore
    val identification: Int,
    @Ignore
    val invite: Invite?,
    @Ignore
    val is_tourist: Int,
    @Ignore
    val official: Official?,
    @Ignore
    val pendant: Pendant?,
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
    val vip: Vip?
) {
    constructor() : this(1,0, 0, "", "", "", 0.0,
        "", 0,0,null,0,null,
        null,0,0,0,0,0,null)
}