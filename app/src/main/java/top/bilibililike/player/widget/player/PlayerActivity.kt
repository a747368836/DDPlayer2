package top.bilibililike.player.widget.player


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

import top.bilibililike.player.common.utilkit.AppBarStateChangeListener
import top.bilibililike.player.supportClass.player.IPlayerStateListener
import kotlin.math.absoluteValue


/**
 *  @author: Xbs
 *  @date:   2020/02/29
 *  @desc:  todo 播放器应该封装成一个Fragment或者啥的，这Api真是吃了屎一样的难受 ！！IJKPlayer决定了倍速对无音轨文件无效！！
 *
 */

class PlayerActivity : MVPActivity<PlayerContract.Presenter>(),
    PlayerContract.View {
    override fun getVideoDetailSuccess(dataBean: AvDescriptionBean.DataBean) {
        presenter.getAvPlayUrl(dataBean.aid.toString(),dataBean.cid.toString(),"32")
    }

    override fun getVideoUrlSuccess(urlDataBean: Data) {
        val dashBean  = urlDataBean.dash
        if (dashBean != null){
            loadPlayer(dashBean.video.get(0).base_url,dashBean.audio.get(0).base_url)
        }else{
            //todo 这部分都是老视频 url是分段的 但是音视频一体 得后续做兼容 要不只有7分钟左右
            loadPlayer(urlDataBean.durl!!.durl.get(0).url)
        }
    }


    override fun getLiveUrlSuccess() {

    }

    override fun getLayoutId(): Int = R.layout.activity_play_av

    override fun bindPresenter(): PlayerContract.Presenter =
        PlayerPresenter(this)

    override fun initView() {
        val params = collapsing_toolbar.layoutParams as AppBarLayout.LayoutParams
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

        }

        fun setPlayerScrollState(){
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
            //video_player.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
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

            //播放器滑动限制
            setPlayerScrollState()

            //音画同步
            video_player.setGSYVideoProgressListener { progress, secProgress, currentPosition, duration ->
                if (audio_player.currentState != CURRENT_STATE_PLAYING) {
                    audio_player.seekTo(currentPosition.toLong())
                    if (audio_player.start.currentState == STATE_PAUSE) audio_player.start.performClick()
                }
                val timeMinus = currentPosition - audio_player.currentPositionWhenPlaying
                if (timeMinus.absoluteValue > 1000) audio_player.seekTo(currentPosition.toLong())
                val rate = (timeMinus + 1000)/1000
                if (timeMinus > 50) audio_player.setSpeed(rate.toFloat(),false)
                else if (timeMinus < -125) audio_player.setSpeed(rate.toFloat(),false)
                else {
                    if (!(audio_player.speed - 1 < 10e-3)) audio_player.setSpeed(1f,false)
                }
                Log.d("PlayerActivity","时间差 = "+timeMinus + "倍数 = "+audio_player.speed)


            }

            //音画操作同步
            video_player.bindStateListener(object :IPlayerStateListener{
                override fun onVideoComplete() {
                    audio_player.seekTo(audio_player.gsyVideoManager.duration)
                    audio_player.onVideoPause()
                    setPlayerScrollState()
                }

                override fun onSetup() {
                    Log.d("PlayerActivity","StateListener onSetup")
                    setPlayerScrollState()
                }

                override fun onVideoReset() {
                    audio_player.onVideoReset()
                    Log.d("PlayerActivity","StateListener Reset")
                    setPlayerScrollState()
                }

                override fun onPause() {
                    audio_player.onVideoPause()
                    Log.d("PlayerActivity","StateListener onPause")
                    setPlayerScrollState()
                }

                override fun onVideoResume(ifSeek:Boolean,target:Long) {
                    audio_player.seekTo(target).takeIf { ifSeek }
                    audio_player.onVideoResume()
                    Log.d("PlayerActivity","StateListener onVideoResume")
                    setPlayerScrollState()
                }

                override fun onStartBuffering() {
                    audio_player.onVideoPause()
                    Log.d("PlayerActivity","StateListener onStartBuffering")
                    setPlayerScrollState()
                }

                override fun onSeekComplete(targetPosition: Long) {
                    audio_player.seekTo(targetPosition)
                    Log.d("PlayerActivity","StateListener onSeekComplete target =" + targetPosition)
                    setPlayerScrollState()
                }

            })


        }

        initAppBar()
        initPlayer()

    }


    override fun initData() {
        val avStr = intent.getStringExtra(Const.INTENT_VIDEO_AV)
        val liveRoomStr = intent.getStringExtra(Const.INTENT_VIDEO_LIVE)
        val bangumiEpStr = intent.getStringExtra(Const.INTENT_VIDEO_BANGUMI)
        val articleCvStr = intent.getStringExtra(Const.INTENT_VIDEO_ARTICLE)
        if (avStr != null){
            presenter.getVideoDetail(avStr)
            Log.d("PlayerActivity","intent av = ${avStr}")
        }else if (liveRoomStr != null){
            presenter.getLiveUrl(liveRoomStr)
        }else if (bangumiEpStr != null){
            presenter.getBangumiPlayUrl()
        }


    }

    private fun loadPlayer(avUrl: String, audioUrl: String) {
        loadPlayer(avUrl)
        if (audioUrl != "null") {
            audio_player.setUp(audioUrl, true, "音频测试")
            audio_player.startAfterPrepared()
        }
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