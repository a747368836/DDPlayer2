package top.bilibililike.player.common.bean.avUrl

data class Audio(
    val backup_url: List<String>,
    val bandwidth: Int,
    val base_url: String,
    val codecid: Int,
    val id: Int,
    val size: Int
)