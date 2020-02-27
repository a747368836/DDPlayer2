package top.bilibililike.player.ui.live.subtitle.core.WebSocket;

public interface DanmakuCallBack {
    /**
     * 来了他来了，同传man来了
     * @param str 同传弹幕（去除【】括号）
     */
    void onShow(String str);
}
