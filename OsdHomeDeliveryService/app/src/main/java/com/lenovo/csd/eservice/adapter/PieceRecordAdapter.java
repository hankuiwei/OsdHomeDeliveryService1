package com.lenovo.csd.eservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.entity.base.ChangeRecordData;

import java.util.ArrayList;

/**
 * Created by 彤 on 2016/9/7.
 */
public class PieceRecordAdapter extends RecyclerView.Adapter<PieceRecordAdapter.PieceViewHolder> {
    private ArrayList<ChangeRecordData.RecordItem> datas;
    private Context context;
    private LayoutInflater inflater;
    private DeleteLinstenner linstenner;
    private boolean hide;

    public PieceRecordAdapter(Context context, ArrayList<ChangeRecordData.RecordItem> datas) {
        this.context = context;
        this.datas = datas;
        inflater = LayoutInflater.from(context);
        if (context instanceof DeleteLinstenner) {
            linstenner = (DeleteLinstenner) context;
        }
    }


    @Override
    public PieceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PieceViewHolder holder = new PieceViewHolder(inflater.inflate(R.layout.changepiece_item_layout, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(PieceViewHolder holder, int position) {
        final ChangeRecordData.RecordItem item = datas.get(position);
        if (hide) {
            holder.mTextDelete.setVisibility(View.GONE);
        } else {
            holder.mTextDelete.setVisibility(View.VISIBLE);
        }
        if (item != null) {

            if (item.getType().equals("change")) {//换件
                holder.mTextDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        linstenner.onDelete(item.getId(),false);
                    }
                });
            holder.mTxtChangeType.setText("【换件."+item.getChange_type()+"】");
                //换件名称修正
                holder.mTxtUppieceSnName.setText("换上件条码：");
                holder.mTxtUppieceCodeName.setText("换上件编码：");
                switch (item.getChange_code()) {
                    case "10":
                        holder.mLinearUp.setVisibility(View.VISIBLE);
                        holder.mLinearDes.setVisibility(View.VISIBLE);
                        holder.mLinearDown.setVisibility(View.VISIBLE);
                        holder.mTxtUppieceSN.setText(item.getMachine_up_sn());
                        holder.mTxtUppieceCode.setText(item.getMachine_up_code());
                        holder.mTxtUppieceDesc.setText(item.getMachine_up_desc());
                        holder.mTxtDownPieceSN.setText(item.getMachine_down_sn());
                        holder.mTxtDownPieceCode.setText(item.getMachine_down_code());
                        holder.mTxtDownPieceDesc.setText(item.getMachine_down_desc());
                        break;
                    case "20":
                        holder.mLinearDes.setVisibility(View.VISIBLE);
                        holder.mLinearUp.setVisibility(View.GONE);
                        holder.mLinearDown.setVisibility(View.VISIBLE);
                        holder.mTxtDownPieceSN.setText(item.getMachine_down_sn());
                        holder.mTxtDownPieceCode.setText(item.getMachine_down_code());
                        holder.mTxtDownPieceDesc.setText(item.getMachine_down_desc());
                        break;
                    case "30":
                        holder.mLinearUp.setVisibility(View.VISIBLE);
                        holder.mLinearDes.setVisibility(View.VISIBLE);
                        holder.mLinearDown.setVisibility(View.GONE);
                        holder.mTxtUppieceSN.setText(item.getMachine_up_sn());
                        holder.mTxtUppieceCode.setText(item.getMachine_up_code());
                        holder.mTxtUppieceDesc.setText(item.getMachine_up_desc());
                        break;
                }
            } else if (item.getType().equals("unchange")) {//非换件
                holder.mTextDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        linstenner.onDelete(item.getId(),true);
                    }
                });
                holder.mTxtChangeType.setText("【非换件】");
                holder.mLinearDown.setVisibility(View.GONE);
                holder.mLinearUp.setVisibility(View.VISIBLE);
                holder.mLinearDes.setVisibility(View.GONE);
                holder.mTxtUppieceSnName.setText("部件大类：");
                holder.mTxtUppieceSN.setText(item.getSp_desc());
                holder.mTxtUppieceCodeName.setText("动作：");
                holder.mTxtUppieceCode.setText(item.getAction_desc());
            }
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class PieceViewHolder extends RecyclerView.ViewHolder {
        private TextView mTxtChangeType;
        private TextView mTextDelete;
        private LinearLayout mLinearUp;
        private LinearLayout mLinearDown;
        private LinearLayout mLinearDes;
        private TextView mTxtUppieceSnName;
        private TextView mTxtUppieceCodeName;
        private TextView mTxtUppieceSN;
        private TextView mTxtUppieceCode;
        private TextView mTxtUppieceDesc;
        private TextView mTxtDownPieceSN;
        private TextView mTxtDownPieceCode;
        private TextView mTxtDownPieceDesc;

        public PieceViewHolder(View itemView) {
            super(itemView);
            mTxtChangeType = (TextView) itemView.findViewById(R.id.text_pieceType);
            mTextDelete = (TextView) itemView.findViewById(R.id.textDeleteItemPice);
            mTxtUppieceSnName = (TextView) itemView.findViewById(R.id.text_upPieceLineCodeName);
            mTxtUppieceCodeName = (TextView) itemView.findViewById(R.id.textUpPieceCodeName);
            mTxtUppieceSN = (TextView) itemView.findViewById(R.id.text_upPieceLineCode);
            mTxtUppieceCode = (TextView) itemView.findViewById(R.id.text_upPieceCode);
            mTxtUppieceDesc = (TextView) itemView.findViewById(R.id.text_upPieceDes);
            mTxtDownPieceSN = (TextView) itemView.findViewById(R.id.text_downPieceLineCode);
            mTxtDownPieceCode = (TextView) itemView.findViewById(R.id.text_downPieceCode);
            mTxtDownPieceDesc = (TextView) itemView.findViewById(R.id.text_downPieceDes);
            mLinearUp = (LinearLayout) itemView.findViewById(R.id.linear_upPiece);
            mLinearDown = (LinearLayout) itemView.findViewById(R.id.linear_downPiece);
            mLinearDes = (LinearLayout) itemView.findViewById(R.id.linear_upPieceDesc);

        }
    }

    /**
     * 刷新数据
     */
    public void refreshData(ArrayList<ChangeRecordData.RecordItem> list) {
        if (this.datas != null) {
            datas.clear();
            datas.addAll(list);
            notifyDataSetChanged();
        }
    }

    public interface DeleteLinstenner {
        void onDelete(String id, boolean unchange);
    }

    /**
     * 隐藏删除按钮
     */
    public void hideDeleteBtn(boolean hide) {
        this.hide = hide;
        notifyDataSetChanged();
    }


}
