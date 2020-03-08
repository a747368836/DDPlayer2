package top.bilibililike.player.widget.player


import android.content.Intent
import android.util.Log
import kotlinx.android.synthetic.main.layout_player.*
import top.bilibililike.mvp.mvp.MVPActivity

import top.bilibililike.player.R

import android.view.View

import tv.danmaku.ijk.media.player.IjkMediaPlayer
import com.shuyu.gsyvideoplayer.player.IjkPlayerManager


import top.bilibililike.player.supportClass.player.CustomManager
import com.google.android.material.appbar.AppBarLayout
import com.shuyu.gsyvideoplayer.video.base.GSYVideoView.*
import kotlinx.android.synthetic.main.layout_video_standard.view.*
import moe.codeest.enviews.ENPlayView.STATE_PAUSE
import top.bilibililike.mvp.constant.Const
import top.bilibililike.player.common.bean.avDescription.AvDescriptionBean
import top.bilibililike.player.common.bean.avUrl.Data
import top.bilibililike.player.common.bean.live.LivePlayUrlBean

import top.bilibililike.player.common.utilkit.AppBarStateChangeListener
import top.bilibililike.player.supportClass.player.IPlayerStateListener
import top.bilibililike.player.widget.player.live.LivePlayerActivity
import kotlin.math.absoluteValue

import com.shuyu.gsyvideoplayer.listener.GSYVideoProgressListener

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


/**
 *  @author: Xbs
 *  @date:   2020/02/29
 *  @desc:  todo 播放器应该封装成一个Fragment或者啥的，这Api真是吃了屎一样的难受 ！！IJKPlayer决定了倍速对无音轨文件无效！！
 *
 */

class PlayerActivity : MVPActivity<PlayerContract.Presenter>(),
    PlayerContract.View {

    var videoTitle: String? = null

    override fun getVideoDetailSuccess(dataBean: AvDescriptionBean.DataBean) {
        video_player.title.setText(dataBean.title)
        presenter.getAvPlayUrl(dataBean.aid.toString(), dataBean.cid.toString(), "32")
    }

    override fun getVideoUrlSuccess(urlDataBean: Data) {
        val dashBean = urlDataBean.dash
        if (dashBean != null) {
            loadPlayer(dashBean.video.get(0).base_url, dashBean.audio.get(0).base_url)
        } else {
            //todo 这部分都是老视频 url是分段的 但是音视频一体 得后续做兼容 要不只有7分钟左右
            // 思路：放多个播放器，重写底下状态栏
            loadPlayer(urlDataBean.durl!!.durl.get(0).url)
        }
    }

    override fun getLiveUrlSuccess(liveUrlBean: LivePlayUrlBean.DataBean) {
        /*val intent = Intent(this, LivePlayerActivity::class.java);
        intent.putExtra("url", liveUrlBean.durl.get(0).url)
        startActivity(intent)*/
        loadPlayer(liveUrlBean.durl.get(0).url)

    }

    override fun getLayoutId(): Int {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        return R.layout.activity_player
    }

    override fun bindPresenter(): PlayerContract.Presenter =
        PlayerPresenter(this)

    override fun initView() {
        val params = collapsing_toolbar.layoutParams as AppBarLayout.LayoutParams
        fun initAppBar() {
            var stateBefore = AppBarStateChangeListener.State.EXPANDED
            appbar_layout.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
                override fun onStateChanged(appBarLayout: AppBarLayout, state: State) {
                    if (state === State.EXPANDED) {
                        tv_title.visibility = View.INVISIBLE
                        stateBefore = State.EXPANDED
                        //avPlayer.setVisibility(View.VISIBLE);
                    } else if (state === State.COLLAPSED) {
                        //折叠状态
                        tv_title.visibility = View.VISIBLE
                        stateBefore = State.COLLAPSED
                    } else {
                        //中间状态
                        if (stateBefore == State.COLLAPSED){
                            tv_title.visibility = View.INVISIBLE
                        }else{
                            tv_title.visibility = View.VISIBLE
                        }

                    }
                }
            })
        }

        fun setPlayerScrollState() {
            if (video_player.currentState == CURRENT_STATE_PLAYING) {
                params.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
            } else {
                params.scrollFlags =
                    AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
            }
            //appbar_layout.isVerticalScrollBarEnabled = true
            collapsing_toolbar.layoutParams = params
        }

        fun initLivePlayer() {
            val headerMap = HashMap<String, String>()
            headerMap.put("Accept", " */*")
            headerMap.put("User-Agent", " Bilibili Freedoooooom/MarkII")
            video_player.mapHeadData = headerMap
            video_player.tag = "video"
            video_player.playPosition = 0
            video_player.setOptions("MultiSampleVideo0")
            video_player.setOptions("MultiSampleVideo0")
            params.scrollFlags =
                AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
            collapsing_toolbar.layoutParams = params
            video_player.setGSYVideoProgressListener(object : GSYVideoProgressListener {
                override fun onProgress(
                    progress: Int,
                    secProgress: Int,
                    currentPosition: Int,
                    duration: Int
                ) {
                    video_player.takeIf { video_player.visibility == View.INVISIBLE }
                        ?.apply { visibility = View.VISIBLE }
                    setPlayerScrollState()
                }
            })

        }

        fun initVideoPlayer() {
            video_player.tag = "video"
            video_player.playPosition = 0
            video_player.setOptions("MultiSampleVideo0")
            video_player.setOptions("MultiSampleVideo0")
            val headerMap = HashMap<String, String>()
            headerMap.put("Accept", " */*")
            headerMap.put("User-Agent", " Bilibili Freedoooooom/MarkII")
            video_player.mapHeadData = headerMap
            video_player.isAutoFullWithSize = true
            video_player.isShowFullAnimation = false
            video_player.setIsTouchWigetFull(true)
            video_player.setIsTouchWiget(true)
            video_player.seekRatio = 0.5f
            tv_title.setOnClickListener {
                video_player.startPlayLogic()
            }
        }

        fun initAudioPlayer() {
            audio_player.tag = "audio"
            audio_player.playPosition = 1
            val headerMap = HashMap<String, String>()
            headerMap.put("Accept", " */*")
            headerMap.put("User-Agent", " Bilibili Freedoooooom/MarkII")
            audio_player.mapHeadData = headerMap
            video_player.setOptions("MultiSampleVideo1")
            video_player.setOptions("MultiSampleVideo1")
        }

        fun bindVideoAndAudio() {
            video_player.setGSYVideoProgressListener { progress, secProgress, currentPosition, duration ->
                video_player.takeIf { video_player.visibility == View.INVISIBLE }
                    ?.apply { visibility = View.VISIBLE }
                if (audio_player.currentState != CURRENT_STATE_PLAYING) {
                    audio_player.seekTo(currentPosition.toLong())
                    if (audio_player.start.currentState == STATE_PAUSE &&
                        video_player.currentState != CURRENT_STATE_PLAYING_BUFFERING_START
                    ) audio_player.start.performClick()
                }
                //播放器滑动限制
                setPlayerScrollState()
                val timeMinus = currentPosition - audio_player.currentPositionWhenPlaying
                if (timeMinus.absoluteValue > 300) audio_player.seekTo(currentPosition.toLong())
                val rate: Float = (timeMinus + 1000) / 1000.0f
                if (timeMinus.absoluteValue > 50) audio_player.setSpeedPlaying(rate, false)
                else {
                    if (((audio_player.speed - 1).absoluteValue <= 0.1)) audio_player.setSpeed(
                        1f,
                        false
                    )
                }
                Log.d("PlayerActivity", "时间差 = " + timeMinus + "倍数 = " + audio_player.speed)


            }

            //播放器操作同步
            video_player.bindStateListener(object : IPlayerStateListener {
                override fun onClickUiToggle() {
                    val state = video_player.currentState;
                    if (video_player.fullWindowPlayer != null) {
                        if (state == CURRENT_STATE_PLAYING) {
                            toolbar.takeIf { toolbar.visibility == View.VISIBLE }
                                ?.apply { visibility = View.INVISIBLE }
                        } else if (state == CURRENT_STATE_PAUSE || state == CURRENT_STATE_AUTO_COMPLETE || state == CURRENT_STATE_NORMAL) {
                            toolbar.visibility = View.VISIBLE
                        }
                    }
                }

                override fun onVideoComplete() {
                    audio_player.seekTo(audio_player.gsyVideoManager.duration)
                    audio_player.onVideoPause()
                    setPlayerScrollState()
                }

                override fun onSetup() {
                    Log.d("PlayerActivity", "StateListener onSetup")
                    setPlayerScrollState()
                }

                override fun onVideoReset() {
                    audio_player.onVideoReset()
                    Log.d("PlayerActivity", "StateListener Reset")
                    setPlayerScrollState()
                }

                override fun onPause() {
                    audio_player.onVideoPause()
                    Log.d("PlayerActivity", "StateListener onPause")
                    setPlayerScrollState()
                }

                override fun onVideoResume(ifSeek: Boolean, target: Long) {
                    audio_player.seekTo(target).takeIf { ifSeek }
                    audio_player.onVideoResume()
                    Log.d("PlayerActivity", "StateListener onVideoResume")
                }

                override fun onStartBuffering() {
                    audio_player.onVideoPause()
                    Log.d("PlayerActivity", "StateListener onStartBuffering")
                }

                override fun onSeekComplete(targetPosition: Long) {
                    audio_player.seekTo(targetPosition)
                    audio_player.onVideoPause()
                    Log.d(
                        "PlayerActivity",
                        "StateListener onSeekComplete target =" + targetPosition
                    )
                }

            })
        }

        fun dispatchVideoTask() {
            val avStr = intent.getStringExtra(Const.INTENT_VIDEO_AV)
            val liveRoomStr = intent.getStringExtra(Const.INTENT_VIDEO_LIVE)
            val bangumiEpStr = intent.getStringExtra(Const.INTENT_VIDEO_BANGUMI)
            val articleCvStr = intent.getStringExtra(Const.INTENT_VIDEO_ARTICLE)
            if (avStr != null) {
                initVideoPlayer()
                initAudioPlayer()
                bindVideoAndAudio()
                initAvData(avStr)
            } else if (liveRoomStr != null) {
                initLivePlayer()
                initLiveData(liveRoomStr)
            } else if (bangumiEpStr != null) {
                initVideoPlayer()
                initBangumiData()
            }
        }
        initAppBar()

        dispatchVideoTask()
    }

    fun initAvData(avString: String) {
        presenter.getVideoDetail(avString)
        Log.d("PlayerActivity", "intent av = ${avString}")
    }

    fun initLiveData(liveRoomStr: String) {
        presenter.getLiveUrl(liveRoomStr)
    }

    fun initBangumiData() {
        presenter.getBangumiPlayUrl()
    }


    private fun loadPlayer(avUrl: String, audioUrl: String) {
        loadPlayer(avUrl)
        if (audioUrl != "null") {
            audio_player.isStartAfterPrepared = false
            audio_player.setUp(audioUrl, false, "音频测试")
            audio_player.isStartAfterPrepared = false
            audio_player.onPrepared()
            Log.d("PlayerActivity", "audio url = $audioUrl")

        }
        //ijk关闭log
        //IjkPlayerManager.setLogLevel(IjkMediaPlayer.IJK_LOG_SILENT)
    }

    private fun loadPlayer(avUrl: String) {
        video_player.setUp(avUrl, false, "播放测试")
        Log.d("PlayerActivity", "av url = $avUrl")
        video_player.startAfterPrepared()
    }


    override fun onPause() {
        super.onPause()
        CustomManager.onPauseAll()
    }

    override fun onDestroy() {
        super.onDestroy()
        CustomManager.clearAllVideo()
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