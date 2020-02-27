package top.bilibililike.player.ui.bangumi




import top.bilibililike.mvp.mvp.MVPActivity
import top.bilibililike.player.R



class bangumiActivity : MVPActivity<bangumiContract.Presenter>(), bangumiContract.View {

    override fun getLayoutId(): Int = R.layout.activity_bangumi

    override fun bindPresenter(): bangumiContract.Presenter = bangumiPresenter(this)
}