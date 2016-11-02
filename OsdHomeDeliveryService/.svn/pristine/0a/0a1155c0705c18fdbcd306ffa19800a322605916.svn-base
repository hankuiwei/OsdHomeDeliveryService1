package com.lenovo.csd.eservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.entity.base.UnusedPartData;

import java.util.ArrayList;

/**
 * Created by 彤 on 2016/8/17.
 */
public class UnusedPartAdapter extends RecyclerView.Adapter<UnusedPartAdapter.UnusedPartHolder> {
    private Context context;
    private ArrayList<UnusedPartData.UnusedPartItemData> datas;

    public UnusedPartAdapter(Context context, ArrayList<UnusedPartData.UnusedPartItemData> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public UnusedPartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_unusedpart_recyclerview, parent, false);
        UnusedPartHolder holder = new UnusedPartHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(UnusedPartHolder holder, int position) {
        UnusedPartData.UnusedPartItemData data = datas.get(position);
        holder.mTxtOrderID.setText(data.getSo_id() + "");
        holder.mTxtOrderStatus.setText(data.getMark_status());
        holder.mTxtPRcode.setText(data.getPr_code());
        holder.mTxtPartDesc.setText(data.getMaterial_name());
        holder.mTxtPartCode.setText(data.getMaterial_no());
        holder.mTxtPartLineCode.setText(data.getSp_sn());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    class UnusedPartHolder extends RecyclerView.ViewHolder {
        public TextView mTxtOrderID;
        public TextView mTxtOrderStatus;
        public TextView mTxtPRcode;
        public TextView mTxtPartDesc;
        public TextView mTxtPartCode;
        public TextView mTxtPartLineCode;

        public UnusedPartHolder(View itemView) {
            super(itemView);
            mTxtOrderID = (TextView) itemView.findViewById(R.id.text_unUsedPartOrderID);
            mTxtOrderStatus = (TextView) itemView.findViewById(R.id.text_unUsedpartorderStatus);
            mTxtPRcode = (TextView) itemView.findViewById(R.id.text_unUsedPartprCode);
            mTxtPartDesc = (TextView) itemView.findViewById(R.id.text_unUsedpartDesc);
            mTxtPartCode = (TextView) itemView.findViewById(R.id.text_unUsedpartCode);
            mTxtPartLineCode = (TextView) itemView.findViewById(R.id.text_UnusedpartLineCode);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    /**
     * 刷新数据
     *
     * @param list 待加载的新数据
     */
    public void refreshData(ArrayList<UnusedPartData.UnusedPartItemData> list) {
        if (this.datas == null)
            return;
        this.datas.clear();
        this.datas.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     */
    public void addData(ArrayList<UnusedPartData.UnusedPartItemData> list) {
        if (this.datas == null)
            return;
        this.datas.addAll(list);
        notifyDataSetChanged();
    }

}
