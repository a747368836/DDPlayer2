package top.bilibililike.player.common.bean.login

data class KeyBean(
    val code: Int,
    val `data`: Data,
    val ts: Int
)

data class Data(
    val hash: String,
    val key: String
)