package top.bilibililike.player.common.bean.live

data class LiverListBean(
    var icon: String,
    var type: String,
    var collectIds: List<Int>,
    var username: String
)