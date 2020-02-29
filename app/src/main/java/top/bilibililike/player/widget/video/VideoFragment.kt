package top.bilibililike.player.widget.video


import top.bilibililike.mvp.mvp.MVPFragment
import top.bilibililike.player.R

/**
 *  @author: Xbs
 *  @date:   2020/02/27
 *  @desc:   $project
 */

class VideoFragment : MVPFragment<VideoContract.Presenter>(), VideoContract.View {
    override fun getTitle(): String = "影视"

    override fun getLayoutId(): Int = R.layout.fragment_video

    override fun bindPresenter(): VideoContract.Presenter = VideoPresenter(this)
}