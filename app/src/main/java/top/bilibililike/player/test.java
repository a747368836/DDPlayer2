package top.bilibililike.player;

import top.bilibililike.player.widget.live.subtitle.utils.Api;
import top.bilibililike.player.widget.live.subtitle.utils.MD5Util;

public class test {
    public static void main(String[] args) {
        String str = "access_key=d36d84031b9d64599d9e9b490552f921&appkey=1d8b6e7d45233436&build=5410000&channel=bili&mobi_app=android&platform=android&statistics=%7B%22appId%22%3A1%2C%22platform%22%3A3%2C%22version%22%3A%225.41.0%22%2C%22abtest%22%3A%22%22%7D&ts=1564645386" + Api.APP_SECRET;
        String str2 = "access_key=72720524b2fc8b6c32ffcb8ced678a21&appkey=1d8b6e7d45233436&build=5410000&channel=bili&mobi_app=android&platform=android&statistics=%7B%22appId%22%3A1%2C%22platform%22%3A3%2C%22version%22%3A%225.41.0%22%2C%22abtest%22%3A%22%22%7D&ts=1582919104"+ Api.APP_SECRET;
        System.out.println(MD5Util.getMD5(str2));
        //1d95a6ce1cef8e417c9a4d37c728395f
    }
}
