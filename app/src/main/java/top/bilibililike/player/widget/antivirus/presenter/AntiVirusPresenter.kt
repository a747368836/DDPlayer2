package top.bilibililike.player.widget.antivirus.presenter

import top.bilibililike.mvp.mvp.BasePresenter
import top.bilibililike.player.widget.antivirus.contract.AntiVirusContract


class AntiVirusPresenter(view: AntiVirusContract.View) : BasePresenter<AntiVirusContract.View>(view),
    AntiVirusContract.Presenter {

}