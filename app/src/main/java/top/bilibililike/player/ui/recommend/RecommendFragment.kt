package top.bilibililike.player.ui.recommend

import top.bilibililike.mvp.mvp.MVPFragment
import top.bilibililike.player.R



class RecommendFragment : MVPFragment<RecommendContract.Presenter>(), RecommendContract.View {
    override fun getTitle(): String {
        return "推荐"
    }

    override fun getLayoutId(): Int = R.layout.activity_recommend

    override fun bindPresenter(): RecommendContract.Presenter = RecommendPresenter(this)
}