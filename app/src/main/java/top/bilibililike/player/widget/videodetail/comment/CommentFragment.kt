package top.bilibililike.player.widget.videodetail.comment

import top.bilibililike.mvp.mvp.MVPFragment
import top.bilibililike.player.R
import top.bilibililike.player.widget.videodetail.introduction.IntroductionContract
import top.bilibililike.player.widget.videodetail.introduction.IntroductionPresenter

/**
 *   Created by xbs on 2020/3/8 14:32
 *   Description:
 */


class CommentFragment : MVPFragment<IntroductionContract.Presenter>(),
    IntroductionContract.View {
    override fun getTitle(): String = "评论"

    override fun getLayoutId(): Int = R.layout.fragment_introduction

    override fun bindPresenter(): IntroductionContract.Presenter =
        IntroductionPresenter(this)
}