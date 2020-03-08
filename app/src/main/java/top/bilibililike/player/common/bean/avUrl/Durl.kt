package top.bilibililike.player.common.bean.avUrl

/**
 *  Project: DDPlayer
 *  Created by Xbs on 2020/3/1
 *  Describe:
 */

data class Durl(
    val backup_url: List<String>,
    val length: Int,
    val order: Int,
    val size: Int,
    val url: String
)