package top.bilibililike.player.common.bean.login.token

data class CookieInfo(
    val cookies: List<Cooky>,
    val domains: List<String>
)