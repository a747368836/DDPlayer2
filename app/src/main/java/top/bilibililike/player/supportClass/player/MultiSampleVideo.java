package top.bilibililike.player.supportClass.player;

import android.content.Context;
import android.graphics.Point;
import android.media.AudioManager;
import android.text.TextUtils;
import android.util.AttributeSet;

import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.shuyu.gsyvideoplayer.utils.Debuger;

import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoViewBridge;

import java.io.File;

import top.bilibililike.player.R;


public class MultiSampleVideo extends StandardGSYVideoPlayer {

    private final static String TAG = "MultiSampleVideo";
    private IPlayerStateListener stateListener;

    ImageView mCoverImage;

    String mCoverOriginUrl;

    int mDefaultRes;

    public MultiSampleVideo(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public MultiSampleVideo(Context context) {
        super(context);
    }

    public MultiSampleVideo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * this method have to be called before setup
     *
     * @param listener callback
     */
    public void bindStateListener(IPlayerStateListener listener) {
        this.stateListener = listener;
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_video_standard;
    }

    @Override
    protected void init(Context context) {
        super.init(context);
        mCoverImage = (ImageView) findViewById(R.id.thumbImage);
        if (mThumbImageViewLayout != null &&
                (mCurrentState == -1 || mCurrentState == CURRENT_STATE_NORMAL || mCurrentState == CURRENT_STATE_ERROR)) {
            mThumbImageViewLayout.setVisibility(VISIBLE);
        }
        onAudioFocusChangeListener = focusChange -> {
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_GAIN:
                    break;
                case AudioManager.AUDIOFOCUS_LOSS:
                    //todo 判断如果不是外界造成的就不处理
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    //todo 判断如果不是外界造成的就不处理
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    break;
            }
        };
    }



    @Override
    public GSYVideoViewBridge getGSYVideoManager() {
        CustomManager.getCustomManager(getKey()).initContext(getContext().getApplicationContext());
        return CustomManager.getCustomManager(getKey());
    }

    @Override
    protected boolean backFromFull(Context context) {
        return CustomManager.backFromWindowFull(context, getKey());
    }

    @Override
    protected void releaseVideos() {
        CustomManager.releaseAllVideos(getKey());
    }


    @Override
    protected int getFullId() {
        return CustomManager.FULLSCREEN_ID;
    }

    @Override
    protected int getSmallId() {
        return CustomManager.SMALL_ID;
    }

    @Override
    public void onSeekComplete() {
        if (stateListener != null) {
            stateListener.onSeekComplete(getGSYVideoManager().getCurrentPosition());
        }
        super.onSeekComplete();

    }


    @Override
    public void onVideoResume(boolean seek) {
        if (stateListener != null) {
            stateListener.onVideoResume(seek,getGSYVideoManager().getCurrentPosition());
        }
        super.onVideoResume(seek);
    }


    @Override
    protected void startButtonLogic() {
        super.startButtonLogic();
    }

    @Override
    public void onVideoReset() {
        super.onVideoReset();
        if (stateListener != null) {
            stateListener.onVideoReset();
        }
    }

    @Override
    public void onVideoPause() {
        super.onVideoPause();
        if (stateListener != null) {
            stateListener.onPause();
        }

    }

    @Override
    protected void clickStartIcon() {
        super.clickStartIcon();
    }



    @Override
    public boolean setUp(String url, boolean cacheWithPlay, File cachePath, String title) {
        if (stateListener != null) {
            stateListener.onSetup();
        }
        return super.setUp(url, cacheWithPlay, cachePath, title);
    }

    @Override
    public void startPlayLogic() {
        super.startPlayLogic();

    }

    @Override
    protected void changeUiToCompleteShow() {
        super.changeUiToCompleteShow();
        if (stateListener != null) {
            stateListener.onVideoComplete();
        }
    }

    public void loadCoverImage(String url, int res) {
        mCoverOriginUrl = url;
        mDefaultRes = res;
        Glide.with(getContext().getApplicationContext())
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .frame(1000000)
                                .centerCrop()
                                .error(res)
                                .placeholder(res))
                .load(url)
                .into(mCoverImage);
    }

    @Override
    public GSYBaseVideoPlayer startWindowFullscreen(Context context, boolean actionBar, boolean statusBar) {
        GSYBaseVideoPlayer gsyBaseVideoPlayer = super.startWindowFullscreen(context, actionBar, statusBar);
        MultiSampleVideo multiSampleVideo = (MultiSampleVideo) gsyBaseVideoPlayer;
        multiSampleVideo.loadCoverImage(mCoverOriginUrl, mDefaultRes);
        return multiSampleVideo;
    }


    @Override
    public GSYBaseVideoPlayer showSmallVideo(Point size, boolean actionBar, boolean statusBar) {
        //下面这里替换成你自己的强制转化
        MultiSampleVideo multiSampleVideo = (MultiSampleVideo) super.showSmallVideo(size, actionBar, statusBar);
        multiSampleVideo.mStartButton.setVisibility(GONE);
        multiSampleVideo.mStartButton = null;
        return multiSampleVideo;
    }

    public String getKey() {
        if (mPlayPosition == -22) {
            Debuger.printfError(getClass().getSimpleName() + " used getKey() " + "******* PlayPosition never set. ********");
        }
        if (TextUtils.isEmpty(mPlayTag)) {
            Debuger.printfError(getClass().getSimpleName() + " used getKey() " + "******* PlayTag never set. ********");
        }
        return TAG + mPlayPosition + mPlayTag;
    }


}



