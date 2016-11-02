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
public class UsedPartAdapter extends RecyclerView.Adapter<UsedPartAdapter.UnusedPartHolder> {
    private Context context;
    private ArrayList<UnusedPartData.UnusedPartItemData> datas;

    public UsedPartAdapter(Context context, ArrayList<UnusedPartData.UnusedPartItemData> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public UnusedPartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_usedpart_recyclerview, parent, false);
        UnusedPartHolder holder = new UnusedPartHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(UnusedPartHolder holder, int position) {
        UnusedPartData.UnusedPartItemData data = datas.get(position);
        holder.mTxtOrderID.setText(data.getSo_id() + "");
        holder.mTxtPartType.setText(data.getMaterial_class_name());
        holder.mTxtPartDesc.setText(data.getMaterial_name());
        holder.mTxtNewPartCode.setText(data.getMaterial_no());
        holder.mTxtOldPartCode.setText(data.getDown_material_no());
        holder.mTxtNewPartLineCode.setText(data.getSp_sn());
        holder.mTxtNewPartLineCode.requestFocus();
        holder.mTxtOldPartLineCode.setText(data.getDown_spsn());
        holder.mTxtOldPartLineCode.requestFocus();
        holder.mTxtLowerPrice.setText(data.getYakuan_price());
        holder.mTxtLowerPriceID.setText(data.getDown_yakuan_price());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    class UnusedPartHolder extends RecyclerView.ViewHolder {
        public TextView mTxtOrderID;
        public TextView mTxtPartType;
        public TextView mTxtPartDesc;
        public TextView mTxtNewPartCode;
        public TextView mTxtOldPartCode;
        public TextView mTxtNewPartLineCode;
        public TextView mTxtOldPartLineCode;
        public TextView mTxtLowerPrice;
        public TextView mTxtLowerPriceID;

        public UnusedPartHolder(View itemView) {
            super(itemView);
            mTxtOrderID = (TextView) itemView.findViewById(R.id.text_usedPartOrderID);
            mTxtPartType = (TextView) itemView.findViewById(R.id.text_usedPartType);
            mTxtPartDesc = (TextView) itemView.findViewById(R.id.text_usedPartDesc);
            mTxtNewPartCode = (TextView) itemView.findViewById(R.id.text_newPartCode);
            mTxtOldPartCode = (TextView) itemView.findViewById(R.id.text_oldPartCode);
            mTxtNewPartLineCode = (TextView) itemView.findViewById(R.id.text_newPartLineCode);
            mTxtOldPartLineCode = (TextView) itemView.findViewById(R.id.text_oldPartLineCode);
            mTxtLowerPrice = (TextView) itemView.findViewById(R.id.text_oldPartLowerPrice);
            mTxtLowerPriceID = (TextView) itemView.findViewById(R.id.text_oldPartID);
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
