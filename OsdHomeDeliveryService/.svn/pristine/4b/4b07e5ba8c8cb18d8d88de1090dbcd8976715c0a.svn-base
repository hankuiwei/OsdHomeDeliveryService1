package com.lenovo.csd.eservice.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
 * Created by 彤 on 2016/8/15.
 */
public class AttachmentAdapter extends RecyclerView.Adapter<AttachmentAdapter.AttachmentHolder> {
    private Context context;
    private ArrayList<Attachment> datas;

    //    private
    public AttachmentAdapter(Context context, ArrayList<Attachment> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public AttachmentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AttachmentHolder attachmentHolder = new AttachmentHolder(LayoutInflater.from(context).inflate(R.layout.item_attachment_recyclerview, parent, false));
        return attachmentHolder;
    }

    @Override
    public void onBindViewHolder(AttachmentHolder holder, int position) {
        final Attachment data = datas.get(position);
        if (data.getFile_id() == null || TextUtils.isEmpty(data.getFile_id())) {//没有附件ID，为本地图片
            Utils.displayCompressedBitmap(context, holder.mImgAttach, data.getFile_path());
        } else {
//            HttpClientManager.displayImage(holder.mImgAttach, data.getFile_path());
//            Utils.loadImageWithGlideCall(context,data.getFile_path(),holder.mImgAttach,R.drawable.default_load_img);
            Utils.loadImageWithGlide(context, data.getFile_path(), holder.mImgAttach, R.drawable.default_load_img);
        }
        holder.mTxtOrderID.setText("工单编号：" + data.getOrder_code());
        holder.mTxtOrderID.requestFocus();
        holder.mTxtOrderType.setText("附件类型：" + data.getType());
        holder.mTxtOrderType.requestFocus();
        holder.mTxtOrderStatus.setText(data.getStatus());
        holder.mCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, data.getFile_id(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class AttachmentHolder extends RecyclerView.ViewHolder {
        private CardView mCard;
        private ImageView mImgAttach;
        private TextView mTxtOrderID;
        private TextView mTxtOrderType;
        private TextView mTxtOrderStatus;

        public AttachmentHolder(View itemView) {
            super(itemView);
            mCard = (CardView) itemView.findViewById(R.id.cardView_attchmetnRecycle);
            mImgAttach = (ImageView) itemView.findViewById(R.id.img_attachmentItem);
            mTxtOrderID = (TextView) itemView.findViewById(R.id.text_orderIdAttachItem);
            mTxtOrderType = (TextView) itemView.findViewById(R.id.text_orderTypeAttachItem);
            mTxtOrderStatus = (TextView) itemView.findViewById(R.id.text_attachmentStatus);
        }
    }
}
