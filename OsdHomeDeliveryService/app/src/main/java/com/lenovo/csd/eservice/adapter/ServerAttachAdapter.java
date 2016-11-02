package com.lenovo.csd.eservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.homedao.bean.Attachment;
import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.util.Utils;

import java.util.ArrayList;

/**
 * Created by 彤 on 2016/8/30.
 */
public class ServerAttachAdapter extends RecyclerView.Adapter<ServerAttachAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Attachment> attachments;
    private LayoutInflater inflater;

    public ServerAttachAdapter(Context context, ArrayList<Attachment> attachments) {
        this.context = context;
        this.attachments = attachments;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(inflater.inflate(R.layout.item_uploadattachfrag_recycler, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Attachment attachment = attachments.get(position);
//        HttpClientManager.displayImage(holder.mImgAttach, attachment.getFile_path());
        Utils.loadImageWithGlideCall(context, attachment.getFile_path(), holder.mImgAttach, R.drawable.default_load_img, attachment.getFile_id());
        holder.mTxtOrderId.setText(attachment.getOrder_code());
        holder.mTxtAttachType.setText(attachment.getType());
        holder.mTxtUpTime.setText(attachment.getCreate_time());
    }

    @Override
    public int getItemCount() {
        return attachments.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImgAttach;
        public TextView mTxtOrderId;
        public TextView mTxtAttachType;
        public TextView mTxtUpTime;

        public ViewHolder(View itemView) {
            super(itemView);
            mImgAttach = (ImageView) itemView.findViewById(R.id.img_attachItem);
            mTxtOrderId = (TextView) itemView.findViewById(R.id.text_orderId);
            mTxtAttachType = (TextView) itemView.findViewById(R.id.text_attachType);
            mTxtUpTime = (TextView) itemView.findViewById(R.id.text_attachUploadTime);
        }
    }

    /**
     * 更新数据
     */
    public void refreshData(ArrayList<Attachment> datas, boolean clear) {
        if (clear)
            this.attachments.clear();
        if (datas != null && datas.size() > 0) {
            attachments.addAll(datas);
        }
        notifyDataSetChanged();
    }

}
