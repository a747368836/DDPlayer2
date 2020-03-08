package top.bilibililike.player.widget.antivirus.ui


import top.bilibililike.mvp.mvp.MVPFragment
import top.bilibililike.player.R
import top.bilibililike.player.widget.antivirus.contract.AntiVirusContract
import top.bilibililike.player.widget.antivirus.presenter.AntiVirusPresenter


class AntiVirusFragment : MVPFragment<AntiVirusContract.Presenter>(),
    AntiVirusContract.View {
    override fun getTitle(): String = "抗击肺炎"

    override fun getLayoutId(): Int = R.layout.fragment_antivirus

    override fun bindPresenter(): AntiVirusContract.Presenter =
        AntiVirusPresenter(this)
}