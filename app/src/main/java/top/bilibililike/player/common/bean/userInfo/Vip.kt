package top.bilibililike.player.common.bean.userInfo

data class Vip(
    val due_date: Long,
    val label: Label,
    val status: Int,
    val theme_type: Int,
    val type: Int,
    val vip_pay_type: Int
)