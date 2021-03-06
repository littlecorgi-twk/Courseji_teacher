package com.littlecorgi.leave.student;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.littlecorgi.leave.R;
import java.util.List;

public class SelectPlotAdapter extends RecyclerView.Adapter<SelectPlotAdapter.SelectHolder> {

    private Context mContext;
    private List<String> mediaDtoList;
    // 最大图片张数
    private int picMax;

    public void setListener(CallbackListener listener) {
        this.listener = listener;
    }

    private CallbackListener listener;

    public interface CallbackListener {

        void add();

        void delete(int position);

        void item(int position, List<String> mList);
    }

    public class SelectHolder extends RecyclerView.ViewHolder {

        private ImageView gallery;
        private ImageView delete;

        public SelectHolder(View itemView) {
            super(itemView);
            gallery = itemView.findViewById(R.id.im_show_gallery);
            delete = itemView.findViewById(R.id.iv_del);
        }
    }

    public SelectPlotAdapter(Context mContext, int max) {
        this.mContext = mContext;
        this.picMax = max;
    }

    public void setImageList(List<String> mList) {
        this.mediaDtoList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SelectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SelectHolder(
                LayoutInflater.from(mContext).inflate(R.layout.item_gallery, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SelectHolder holder, int position) {
        // 当前位置大于图片数量并且小于最大减一
        if (position >= mediaDtoList.size() && position <= (picMax - 1)) {
            // 显示添加图片按钮，并显示删除按钮
            Tools.showGlide(mContext, holder.gallery, "");
            holder.delete.setVisibility(View.GONE);
        } else {
            // 显示本地或网络图片，并显示删除按钮
            Tools.showGlide(mContext, holder.gallery, mediaDtoList.get(position));
            holder.delete.setVisibility(View.VISIBLE);
        }
        // 按钮删除事件
        holder.delete.setOnClickListener(
                v -> {
                    // 传入position删除第几张
                    listener.delete(position);
                });
        holder.gallery.setOnClickListener(
                v -> {
                    // 添加新图片点击事件（回调activity）
                    if (position >= mediaDtoList.size() && position <= (picMax - 1)) {
                        listener.add();
                    } else {
                        // 点击查看图片事件，并将最新list传入activity
                        listener.item(position, mediaDtoList);
                    }
                });
    }

    @Override
    public int getItemCount() {
        if (mediaDtoList == null || mediaDtoList.size() == 0) {
            return 1;
        } else {
            return this.mediaDtoList.size() >= picMax ? picMax : this.mediaDtoList.size() + 1;
        }
    }
}
