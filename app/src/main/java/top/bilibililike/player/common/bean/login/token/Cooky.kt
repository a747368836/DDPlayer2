package top.bilibililike.player.common.bean.login.token

data class Cooky(
    val expires: Int,
    val http_only: Int,
    val name: String,
    val value: String
)