package top.bilibililike.player.ui.live.liveFragment


import top.bilibililike.mvp.mvp.BaseContract

class LiveContract : BaseContract{
    interface ILivePresenter : BaseContract.Presenter{
        fun getLivers(roomId:Array<String>)
    }

    interface ILiveView : BaseContract.View
}

