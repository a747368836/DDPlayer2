package top.bilibililike.player.widget.videodetail.introduction


import android.content.Intent
import android.text.TextUtils
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_introduction.*
import kotlinx.android.synthetic.main.layout_player_push.*
import top.bilibililike.mvp.constant.Const
import top.bilibililike.mvp.ext.Toasts
import top.bilibililike.mvp.mvp.MVPFragment
import top.bilibililike.player.R
import top.bilibililike.player.common.bean.avDescription.AvDescriptionBean
import top.bilibililike.player.common.utilkit.FormatUtils
import top.bilibililike.player.support.SmallLineDecoration
import top.bilibililike.player.widget.live.subtitle.utils.ToastUtil
import top.bilibililike.player.widget.player.PlayerActivity

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

    override fun initView() {

        val dataBean: AvDescriptionBean.DataBean? = bundle?.getParcelable("dataBean")
        if (dataBean == null) return
        val mAdapter = IntroductionRecommendListAdapter()
        recyclerview_recommend.adapter = mAdapter
        recyclerview_recommend.layoutManager = LinearLayoutManager(context)
        recyclerview_recommend.addItemDecoration(SmallLineDecoration())
        mAdapter.setNewData(dataBean.relates)
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val item = adapter.getItem(position) as AvDescriptionBean.DataBean.RelatesBean
            val intent = Intent()
            if (TextUtils.equals(item.gotoX,"av")) {
                intent.setClass(context!!, PlayerActivity::class.java)
                intent.putExtra(Const.INTENT_VIDEO_AV, item.param)
                startActivity(intent)
            }else{
                ToastUtil.show("暂不支持该类型")
            }
        }
        Glide.with(this)
            .load(dataBean.owner?.face)
            .into(imv_avatar)
        tv_nickname.setText(dataBean.owner?.name)
        tv_fans.setText(FormatUtils.getNum(dataBean.owner_ext.fans))
        tv_video_title.setText(dataBean.title)
        tv_plays.setText(FormatUtils.getNum(dataBean.stat.view))
        tv_danmu_nums.setText(FormatUtils.getNum(dataBean.stat.danmaku))
        tv_publish_time.setText("${dataBean.pubdate}")
        tv_av.setText("AV"+dataBean.stat?.aid)
        tv_description.setText(dataBean.desc)
        txv_push_good.setText("${dataBean.stat?.like}")
        txv_push_coin.setText("${dataBean.stat?.coin}")
        txv_push_star.setText("${dataBean.stat?.favorite}")


    }

    override fun initData() {

    }
}