package top.bilibililike.player.ui.antivirus


import top.bilibililike.mvp.mvp.MVPFragment
import top.bilibililike.player.R



class AntiVirusFragment : MVPFragment<AntiVirusContract.Presenter>(), AntiVirusContract.View {
    override fun getTitle(): String = "抗击肺炎"

    override fun getLayoutId(): Int = R.layout.activity_antivirus

    override fun bindPresenter(): AntiVirusContract.Presenter = AntiVirusPresenter(this)
}