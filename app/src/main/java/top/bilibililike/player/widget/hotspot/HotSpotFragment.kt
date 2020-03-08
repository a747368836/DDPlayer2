package top.bilibililike.player.widget.hotspot

import top.bilibililike.mvp.mvp.MVPFragment
import top.bilibililike.player.R

/**
 *  Project: DDPlayer
 *  Created by Xbs on 2020/2/27
 *  Describe:
 */

class HotSpotFragment : MVPFragment<HotSpotContract.IHotSpotPresenter>(), HotSpotContract.IHotSpotView{
    override fun bindPresenter(): HotSpotContract.IHotSpotPresenter {
        return HotSpotPresenter()

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_hotspot
    }

    override fun getTitle(): String {
        return "热门"
    }

}