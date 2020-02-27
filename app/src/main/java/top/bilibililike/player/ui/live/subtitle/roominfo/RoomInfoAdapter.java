package top.bilibililike.player.ui.live.subtitle.roominfo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import top.bilibililike.player.R;
import top.bilibililike.player.ui.live.subtitle.bean.RepoBean;


/**
 * @author Xbs
 */
public class RoomInfoAdapter extends RecyclerView.Adapter<RoomInfoAdapter.ViewHolder> {

    private List<RepoBean.DataBean> roomList;
    private ClickCallback clickCallback;

    public RoomInfoAdapter(ClickCallback callback){
        this.clickCallback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindItem(roomList.get(position));
    }

    @Override
    public int getItemCount() {
        if (roomList != null){
            return roomList.size();
        }else {
            return 0;
        }
    }

    public void refreshData(List<RepoBean.DataBean> dataBeans){
        roomList = dataBeans;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView coverImg;
        TextView tvTitle;
        TextView tvUpNickname;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            coverImg = itemView.findViewById(R.id.coverImg);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvUpNickname = itemView.findViewById(R.id.tv_up_nickname);
        }
        public void bindItem(RepoBean.DataBean dataBean){
            RepoBean.DataBean.RoomInfoBean roomInfoBean = dataBean.getRoom_info();
            RepoBean.DataBean.AnchorInfoBean anchorInfoBean = dataBean.getAnchor_info();
            itemView.setOnClickListener( v -> clickCallback.onClicked(roomInfoBean.getRoom_id()+""));
            Glide.with(itemView)
                    .load(roomInfoBean.getCover())
                    .into(coverImg);
            tvTitle.setText(roomInfoBean.getTitle());
            tvUpNickname.setText(anchorInfoBean.getBase_info().getUname());
        }



    }

    public interface ClickCallback {
        /**
         * @param roomId 房间id号
         * 点击监听回调
         */
        void onClicked(String roomId);
    }
}
