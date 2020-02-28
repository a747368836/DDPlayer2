package top.bilibililike.player

import top.bilibililike.player.common.sign.Signer

/**
 *  Project: DDPlayer
 *  Created by Xbs on 2020/2/28
 *  Describe:
 */

fun main() {
    val str = "access_key=714c96f2612cd82609c480ee85b43211&actionKey=appkey&appkey=1d8b6e7d45233436&build=5521100&channel=xiaomi&device=android&mobi_app=android&platform=android&room_id=10209381&statistics=%7B%22appId%22%3A1%2C%22platform%22%3A3%2C%22version%22%3A%225.52.1%22%2C%22abtest%22%3A%22890%22%7D&ts=1579088024"
    val sterr ="appkey=1d8b6e7d45233436&build=5410000&channel=bili&column=2&mobi_app=android&platform=android&pull=true&statistics=%7B%22appId%22%3A1%2C%22platform%22%3A3%2C%22version%22%3A%225.41.0%22%2C%22abtest%22%3A%22%22%7D&ts=1582890749560c52ccd288fed045859ed18bffd973"
    print(Signer.getSign(sterr))
    //500555e85ee33153bb051b932c3f5616
}