package top.bilibililike.player.widget.player.playav


import android.util.Log
import android.util.TimeUtils
import kotlinx.android.synthetic.main.layout_player.*
import top.bilibililike.mvp.mvp.MVPActivity

import top.bilibililike.player.R

import android.view.View
import android.view.ViewGroup

import io.reactivex.android.schedulers.AndroidSchedulers
import tv.danmaku.ijk.media.player.IjkMediaPlayer
import com.shuyu.gsyvideoplayer.player.IjkPlayerManager


import top.bilibililike.player.supportClass.player.CustomManager
import com.google.android.material.appbar.AppBarLayout
import com.shuyu.gsyvideoplayer.listener.GSYVideoProgressListener
import com.shuyu.gsyvideoplayer.listener.VideoAllCallBack
import com.shuyu.gsyvideoplayer.video.base.GSYVideoView.*
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.layout_video_standard.view.*
import moe.codeest.enviews.ENPlayView.STATE_PAUSE
import moe.codeest.enviews.ENPlayView.STATE_PLAY

import top.bilibililike.player.common.utilkit.AppBarStateChangeListener
import top.bilibililike.player.common.utilkit.Utils
import top.bilibililike.player.supportClass.player.IPlayerStateListener
import java.util.concurrent.TimeUnit
import kotlin.math.absoluteValue


/**
 *  @author: Xbs
 *  @date:   2020/02/29
 *  @desc:
 */

class PlayAvActivity : MVPActivity<PlayAvContract.Presenter>(), PlayAvContract.View {

    override fun getLayoutId(): Int = R.layout.activity_play_av

    override fun bindPresenter(): PlayAvContract.Presenter = PlayAvPresenter(this)

    override fun initView() {
        fun initAppBar() {
            appbar_layout.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
                override fun onStateChanged(appBarLayout: AppBarLayout, state: State) {
                    if (state === State.EXPANDED) {
                        //展开状态
                        tx_title.visibility = View.GONE
                        //avPlayer.setVisibility(View.VISIBLE);

                    } else if (state === State.COLLAPSED) {
                        //折叠状态
                        tx_title.visibility = View.VISIBLE
                        // avPlayer.setVisibility(View.INVISIBLE);

                    } else {
                        //中间状态
                        // avPlayer.setVisibility(View.VISIBLE);
                        tx_title.visibility = View.INVISIBLE
                    }
                }
            })
            val params = collapsing_toolbar.layoutParams as AppBarLayout.LayoutParams
            if (video_player.currentState == CURRENT_STATE_PLAYING) {
                params.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
            } else {
                params.scrollFlags =
                    AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
            }
            appbar_layout.isVerticalScrollBarEnabled = true
        }

        fun initPlayer() {

            audio_player.tag = "audio"
            video_player.tag = "video"
            video_player.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            video_player.playPosition = 0
            audio_player.playPosition = 1
            val headerMap = HashMap<String, String>()
            headerMap.put("Accept", "*/*")
            headerMap.put("User-Agent", "Bilibili Freedoooooom/MarkII")
            video_player.mapHeadData = headerMap
            audio_player.mapHeadData = headerMap
            //audio_player.titleTextView.setText("这是一个用来测试能不能实时滚动的测试标题1234567890-=")
            video_player.isAutoFullWithSize = true
            video_player.isShowFullAnimation = true
            video_player.isShowFullAnimation = false
            video_player.setIsTouchWigetFull(true)
            video_player.setIsTouchWiget(true)
            video_player.setThumbPlay(true)
            video_player.seekRatio = 0.5f

            video_player.setGSYVideoProgressListener { progress, secProgress, currentPosition, duration ->
                if (audio_player.currentState != CURRENT_STATE_PLAYING) {
                    audio_player.seekTo(currentPosition.toLong())
                    if (audio_player.start.currentState == STATE_PAUSE) audio_player.start.performClick()
                }
                val timeMinus = currentPosition - audio_player.currentPositionWhenPlaying
                if (timeMinus > 50) audio_player.setSpeed(1.2f,false)
                else if (timeMinus < -125) audio_player.setSpeed(0.8f,false)
                else audio_player.setSpeed(1f,false)
                Log.d("PlayAvActivity","时间差 = "+timeMinus + "倍数 = "+video_player.speed)
            }

            video_player.bindStateListener(object :IPlayerStateListener{
                override fun onVideoComplete() {
                    audio_player.seekTo(audio_player.gsyVideoManager.duration)
                    audio_player.onVideoPause()
                }

                override fun onSetup() {
                    Log.d("PlayAvActivity","StateListener onSetup")
                }

                override fun onVideoReset() {
                    audio_player.onVideoReset()
                    Log.d("PlayAvActivity","StateListener Reset")
                }

                override fun onPause() {
                    audio_player.onVideoPause()
                    Log.d("PlayAvActivity","StateListener onPause")
                }

                override fun onVideoResume(ifSeek:Boolean,target:Long) {
                    audio_player.seekTo(target).takeIf { ifSeek }
                    audio_player.onVideoResume()
                    Log.d("PlayAvActivity","StateListener onVideoResume")
                }

                override fun onStartBuffering() {
                    audio_player.onVideoPause()
                    Log.d("PlayAvActivity","StateListener onStartBuffering")
                }

                override fun onSeekComplete(targetPosition: Long) {
                    audio_player.seekTo(targetPosition)
                    Log.d("PlayAvActivity","StateListener onSeekComplete target =" + targetPosition)
                }

            })


        }

        initAppBar()
        initPlayer()

    }

    var disposable: Disposable? = null;

    override fun initData() {
        val video =
            "http://112.25.54.207/upgcxcode/94/96/108379694/108379694-1-30032.m4s?expires=1582971300&platform=android&ssig=8UZvQ08htHcGV1BqLx9eaA&oi=614314280&trid=c76481bc9c624a7e982a6279dbdbdd04u&nfc=1&nfb=maPYqpoel5MI3qOUX6YpRA==&mid=0"
        val audio =
            "http://112.25.54.203/upgcxcode/94/96/108379694/108379694-1-30216.m4s?expires=1582971300&platform=android&ssig=7EPd6mgVhTp3A1UALe1THg&oi=614314280&trid=c76481bc9c624a7e982a6279dbdbdd04u&nfc=1&nfb=maPYqpoel5MI3qOUX6YpRA==&mid=0"
        loadPlayer(video,audio)
        Log.d("PlayActivity", "video duration = ${video_player.gsyVideoManager.duration}")
        Log.d("PlayActivity", "audio duration = ${audio_player.gsyVideoManager.duration}")


    }

    private fun loadPlayer(avUrl: String, audioUrl: String) {
        if (audioUrl != "null") {
            audio_player.setUp(audioUrl, true, "音频测试")
            audio_player.startAfterPrepared()
        }
        loadPlayer(avUrl)
        //ijk关闭log
        IjkPlayerManager.setLogLevel(IjkMediaPlayer.IJK_LOG_SILENT)
    }

    private fun loadPlayer(avUrl: String) {
        video_player.setUp(avUrl, true, "播放测试")
        video_player.startAfterPrepared()
    }


    override fun onPause() {
        super.onPause()
        CustomManager.onPauseAll()
    }

    override fun onDestroy() {
        super.onDestroy()
        CustomManager.clearAllVideo()
        if(disposable != null && !disposable!!.isDisposed){
            disposable!!.dispose()
            disposable = null
        }
    }

    override fun onResume() {
        super.onResume()
        CustomManager.onResumeAll()
    }

    override fun onBackPressed() {
        if (video_player.isIfCurrentIsFullscreen) {
            video_player.fullWindowPlayer.fullscreenButton.performClick()
        } else {
            super.onBackPressed()
        }

    }
}