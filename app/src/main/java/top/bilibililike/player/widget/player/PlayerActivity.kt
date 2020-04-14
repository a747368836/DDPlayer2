package top.bilibililike.player.widget.player


import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.shuyu.gsyvideoplayer.listener.GSYVideoProgressListener
import com.shuyu.gsyvideoplayer.video.base.GSYVideoView.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.layout_player.*
import kotlinx.android.synthetic.main.layout_video_standard.view.*
import moe.codeest.enviews.ENPlayView.STATE_PAUSE
import top.bilibililike.mvp.base.BaseFragment
import top.bilibililike.mvp.constant.Const
import top.bilibililike.mvp.mvp.MVPActivity
import top.bilibililike.player.R
import top.bilibililike.player.common.bean.avDescription.AvDescriptionBean
import top.bilibililike.player.common.bean.avUrl.Data
import top.bilibililike.player.common.bean.live.LivePlayUrlBean
import top.bilibililike.player.common.utilkit.AppBarStateChangeListener
import top.bilibililike.player.support.MyPagerAdapter
import top.bilibililike.player.support.player.CustomManager
import top.bilibililike.player.support.player.IPlayerStateListener
import top.bilibililike.player.widget.videodetail.comment.CommentFragment
import top.bilibililike.player.widget.videodetail.introduction.IntroductionFragment
import kotlin.math.absoluteValue


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
        videoTitle = dataBean.title
        tv_title.text = videoTitle
        initVideoIntroduction(dataBean)
        presenter.getAvPlayUrl(dataBean.aid.toString(), dataBean.cid.toString(), "32")
    }

    override fun getVideoUrlSuccess(urlDataBean: Data) {
        val dashBean = urlDataBean.dash
        if (dashBean != null) {
            loadPlayer(dashBean.video.get(0).base_url, dashBean.audio.get(0).base_url)
        } else {
            //todo 这部分都是老视频 url是分段的 但是音视频一体 得后续做兼容 要不只有7分钟左右
            // 思路：放多个播放器在同一个位置，重写底下状态栏，简单粗暴，但可能得不到解决就被腰斩了。
            loadPlayer(urlDataBean.durl!!.durl.get(0).url)
        }
    }

    override fun getLiveUrlSuccess(liveUrlBean: LivePlayUrlBean.DataBean) {
        loadPlayer(liveUrlBean.durl.get(0).url)

    }

    override fun getLayoutId(): Int {
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
                        tv_play.visibility = View.GONE
                        tv_title.visibility = View.VISIBLE
                        stateBefore = State.EXPANDED
                        //avPlayer.setVisibility(View.VISIBLE);
                    } else if (state === State.COLLAPSED) {
                        //折叠状态
                        tv_play.visibility = View.VISIBLE
                        tv_title.visibility = View.GONE
                        stateBefore = State.COLLAPSED
                    } else {
                        //中间状态
                        if (stateBefore == State.COLLAPSED) {
                            tv_play.visibility = View.GONE
                            tv_title.visibility = View.GONE
                        } else {
                            tv_play.visibility = View.VISIBLE
                            tv_title.visibility = View.GONE
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
            tv_play.setOnClickListener {
                video_player.startPlayLogic()
            }
            video_player.fullscreenButton.setOnClickListener {
                video_player.startWindowFullscreen(this, false, false);
            }
            video_player.backButton.setOnClickListener { onBackPressed() }
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
        setSupportActionBar(toolbar)
        dispatchVideoTask()
    }

    fun initVideoIntroduction(dataBean: AvDescriptionBean.DataBean) {
        val introductionFragment = IntroductionFragment()
        val bundle = Bundle()
        bundle.putParcelable("dataBean", dataBean);
        introductionFragment.arguments = bundle;
        val commentFragment = CommentFragment()
        val fragmentList = ArrayList<BaseFragment>()
        fragmentList.add(introductionFragment)
        fragmentList.add(commentFragment)
        viewPager.adapter = MyPagerAdapter(supportFragmentManager, fragmentList)
        tab_layout.addTab(tab_layout.newTab().setText("简介"))
        tab_layout.addTab(tab_layout.newTab().setText("评论"))
        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

        })
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                tab_layout.selectTab(tab_layout.getTabAt(position))
            }

        })
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
            audio_player.setUp(audioUrl, false,videoTitle)
            audio_player.isStartAfterPrepared = false
            audio_player.onPrepared()
            Log.d("PlayerActivity", "audio url = $audioUrl")

        }
        //ijk关闭log
        //IjkPlayerManager.setLogLevel(IjkMediaPlayer.IJK_LOG_SILENT)
    }

    private fun loadPlayer(avUrl: String) {
        video_player.setUp(avUrl, false, videoTitle)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val tvItem = menu?.add("tv")
        tvItem?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        tvItem?.setIcon(android.R.drawable.ic_menu_send)
        val share = menu?.add("share")
        share?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        share?.setIcon(android.R.drawable.ic_menu_send)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val title = item?.title.toString()
        if ("tv" == title) { //todo

        } else if ("share" == title) {

        }
        return super.onOptionsItemSelected(item)
    }
}