package top.bilibililike.player.widget.bangumi


import top.bilibililike.mvp.mvp.MVPFragment
import top.bilibililike.player.R

/**
 *  @author: Xbs
 *  @date:   2020/02/27
 *  @desc:   $project
 */

class BangumiFragment : MVPFragment<BangumiContract.Presenter>(), BangumiContract.View {
    override fun getTitle(): String = "番剧"

    override fun getLayoutId(): Int = R.layout.fragment_bangumi

    override fun bindPresenter(): BangumiContract.Presenter = BangumiPresenter(this)
}