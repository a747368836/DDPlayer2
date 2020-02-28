package top.bilibililike.player.common.bean.login.token

data class Data(
    val cookie_info: CookieInfo,
    val sso: List<String>,
    val status: Int,
    val token_info: TokenInfo
)