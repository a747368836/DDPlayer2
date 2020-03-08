package top.bilibililike.player.common.bean.avUrl

data class Data(
    val accept_description: List<String>,
    val accept_format: String,
    val accept_quality: List<Int>,
    val dash: Dash?,
    val durl:DurlX?,
    val fnval: Int,
    val fnver: Int,
    val format: String,
    val no_rexcode: Int,
    val quality: Int,
    val timelength: Int,
    val video_codecid: Int,
    val video_project: Boolean
)