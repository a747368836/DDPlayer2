package top.bilibililike.player.supportClass.player;

public interface IPlayerStateListener {
    //设置好url之类的信息 CURRENT_STATE_NORMAL
    void onSetup();

    //视频重置 CURRENT_STATE_NORMAL
    void onVideoReset();

    //视频暂停
    void onPause();

    //从暂停状态恢复
    void onVideoResume(boolean ifSeek,long target);

    //开始缓冲
    void onStartBuffering();

    //发生了seek跳转事件
    void onSeekComplete(long targetPosition);

    //放完了
    void onVideoComplete();

    //ui被点了
    void onClickUiToggle();

}
