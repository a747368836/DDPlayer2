package top.bilibililike.player.widget.live.subtitle.roominfo;

import android.media.Image;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import top.bilibililike.player.R;
import top.bilibililike.player.widget.live.subtitle.bean.RepoBean;

public class RoomInfoAdapter extends BaseQuickAdapter<RepoBean.DataBean, BaseViewHolder> {
    public RoomInfoAdapter(int layoutResId, @Nullable List<RepoBean.DataBean> data) {
        super(layoutResId, data);
    }

    public RoomInfoAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, RepoBean.DataBean dataBean) {
        RepoBean.DataBean.RoomInfoBean infoBean = dataBean.getRoom_info();
        baseViewHolder.setText(R.id.tv_title,infoBean.getTitle())
                .setText(R.id.tv_up_nickname,dataBean.getAnchor_info().getBase_info().getUname());
        Glide.with(baseViewHolder.itemView)
                .load(infoBean.getCover())
                .into((ImageView) baseViewHolder.getView(R.id.coverImg));
    }
}
