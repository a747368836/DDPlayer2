package top.bilibililike.player.widget.videodetail.introduction


import top.bilibililike.mvp.mvp.MVPFragment
import top.bilibililike.player.R

/**
 *  @author: Xbs
 *  @date:   2020/03/01
 *  @desc:   
 */

class IntroductionFragment : MVPFragment<IntroductionContract.Presenter>(),
    IntroductionContract.View {
    override fun getTitle(): String = "简介"

    override fun getLayoutId(): Int = R.layout.fragment_introduction

    override fun bindPresenter(): IntroductionContract.Presenter =
        IntroductionPresenter(this)
}