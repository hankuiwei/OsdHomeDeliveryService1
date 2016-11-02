package com.lenovo.csd.eservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.http.callback.NoDoubleClickLinstenner;
import com.lenovo.csd.eservice.util.Utils;

import java.util.ArrayList;

/**
 * Created by 彤 on 2016/8/27.
 */
public class SuggestionPicAdapter extends RecyclerView.Adapter<SuggestionPicAdapter.PicHolder> {
    private Context context;
    private ArrayList<String> filePaths;
    private LayoutInflater inflater;
    private OnAddPicLinstenner linstenner;

    public SuggestionPicAdapter(Context context, ArrayList<String> filePaths, OnAddPicLinstenner linstenner) {
        this.context = context;
        this.linstenner = linstenner;
        this.filePaths = filePaths;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public PicHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PicHolder picHolder = new PicHolder(inflater.inflate(R.layout.item_fadeback_gridrecycle, parent, false));
        return picHolder;
    }

    @Override
    public void onBindViewHolder(PicHolder holder, int position) {
        if (position < filePaths.size()) {//显示图片的位置
            String path = filePaths.get(position);
            Utils.displayCompressedBitmap(context, holder.mImage, path);
            holder.frameParent.setEnabled(false);
        } else if (position == filePaths.size()) {//添加图片的按钮显示在最后一位
            if (position < 5) {
                holder.frameParent.setVisibility(View.VISIBLE);
                holder.frameParent.setOnClickListener(new NoDoubleClickLinstenner() {
                    @Override
                    public void doClick(View view) {
                        linstenner.onAddClick();
                    }
                });
            } else if (position == 5) {
                holder.frameParent.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public int getItemCount() {
        return filePaths.size() + 1;
    }

    public class PicHolder extends RecyclerView.ViewHolder {
        public ImageView mImage;
        public FrameLayout frameParent;

        public PicHolder(View itemView) {
            super(itemView);
            mImage = (ImageView) itemView.findViewById(R.id.image_fadebackItemPic);
            frameParent = (FrameLayout) itemView.findViewById(R.id.frameParent);
        }
    }

    public interface OnAddPicLinstenner {
        void onAddClick();
    }

    /**
     * 添加数据
     */
    public void addPic(String filePath) {
        this.filePaths.add(filePath);
        notifyDataSetChanged();
    }
}
