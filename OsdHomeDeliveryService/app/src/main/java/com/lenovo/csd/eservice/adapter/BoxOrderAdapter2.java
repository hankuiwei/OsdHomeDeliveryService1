package com.lenovo.csd.eservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.entity.base.BoxOrderData;
import com.lenovo.csd.eservice.entity.base.BoxOrderData2;
import com.lenovo.csd.eservice.entity.base.TypeData;

import java.util.ArrayList;

/**
 * Created by 彤 on 2016/8/20.
 */
public class BoxOrderAdapter2 extends RecyclerView.Adapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<TypeData> datas;


    public BoxOrderAdapter2(Context context, ArrayList<TypeData> datas) {
        this.context = context;
        this.datas = datas;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 10) {
            return new HistoryHolder(inflater.inflate(R.layout.item_historyholder_boxfrag, parent, false));

        } else if (viewType == 11) {
            return new BoxHolder(inflater.inflate(R.layout.item_boxorder_listview, parent, false));

        } else if (viewType == 12) {
            return new BareboneHolder(inflater.inflate(R.layout.item_barebone_boxfrag, parent, false));

        } else if (viewType == 14) {
            return new TitleHolder(inflater.inflate(R.layout.item_title_boxfrag, parent, false));

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TypeData data = datas.get(position);
        if (holder instanceof HistoryHolder) {
            ((HistoryHolder) holder).mTxtRepairTime.setText(((BoxOrderData2.ChangeHisItem) data).getCreate_time());
            ((HistoryHolder) holder).mTxtNewpieceCode.setText(((BoxOrderData2.ChangeHisItem) data).getUp_material_no());
            ((HistoryHolder) holder).mTxtNewpieceLine.setText(((BoxOrderData2.ChangeHisItem) data).getUp_parts_sn());
            ((HistoryHolder) holder).mTxtNewpieceDesc.setText(((BoxOrderData2.ChangeHisItem) data).getUp_material_no_desc());
            ((HistoryHolder) holder).mTxtOldpieceCode.setText(((BoxOrderData2.ChangeHisItem) data).getDown_material_no());
            ((HistoryHolder) holder).mTxtOldpieceLine.setText(((BoxOrderData2.ChangeHisItem) data).getDown_parts_sn());
            ((HistoryHolder) holder).mTxtOldpieceDesc.setText(((BoxOrderData2.ChangeHisItem) data).getDown_material_no_desc());
        } else if (holder instanceof BoxHolder) {
            ((BoxHolder) holder).mTxtPieceType.setText(((BoxOrderData2.BoxBeanItem) data).getMaterial_category());
            ((BoxHolder) holder).mTxtPieceCode.setText(((BoxOrderData2.BoxBeanItem) data).getMaterial_id());
            ((BoxHolder) holder).mTxtPieceName.setText(((BoxOrderData2.BoxBeanItem) data).getMaterial_name());
        } else if (holder instanceof BareboneHolder) {
            ((BareboneHolder) holder).mTxtPieceType.setText(((BoxOrderData2.BareBoneItem) data).getMaterial_category());
            ((BareboneHolder) holder).mTxtPieceCode.setText(((BoxOrderData2.BareBoneItem) data).getMaterial_id());
            ((BareboneHolder) holder).mTxtPieceName.setText(((BoxOrderData2.BareBoneItem) data).getMaterial_name());
        } else if (holder instanceof TitleHolder) {
            ((TitleHolder) holder).mTxtTitle.setText(((BoxOrderData2.TitleItem) data).getTitleName());
        }
    }

    @Override
    public int getItemViewType(int position) {
        TypeData data = datas.get(position);
        int type = data.getType();
        return type;
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class HistoryHolder extends RecyclerView.ViewHolder {
        public TextView mTxtRepairTime;
        public TextView mTxtNewpieceCode;
        public TextView mTxtNewpieceLine;
        public TextView mTxtNewpieceDesc;
        public TextView mTxtOldpieceCode;
        public TextView mTxtOldpieceLine;
        public TextView mTxtOldpieceDesc;

        public HistoryHolder(View itemView) {
            super(itemView);
            mTxtRepairTime = (TextView) itemView.findViewById(R.id.text_pieceRepairTime);
            mTxtNewpieceCode = (TextView) itemView.findViewById(R.id.text_pieceNewCode);
            mTxtNewpieceLine = (TextView) itemView.findViewById(R.id.text_pieceNewLineCode);
            mTxtNewpieceDesc = (TextView) itemView.findViewById(R.id.text_pieceNewDesc);
            mTxtOldpieceCode = (TextView) itemView.findViewById(R.id.text_pieceOldCode);
            mTxtOldpieceLine = (TextView) itemView.findViewById(R.id.text_pieceOldLineCode);
            mTxtOldpieceDesc = (TextView) itemView.findViewById(R.id.text_pieceOldDesc);
        }
    }


    class BoxHolder extends RecyclerView.ViewHolder {
        public TextView mTxtPieceType;
        public TextView mTxtPieceCode;
        public TextView mTxtPieceName;

        public BoxHolder(View itemView) {
            super(itemView);
            mTxtPieceType = (TextView) itemView.findViewById(R.id.text_pieceType);
            mTxtPieceCode = (TextView) itemView.findViewById(R.id.text_pieceCode);
            mTxtPieceName = (TextView) itemView.findViewById(R.id.text_pieceName);
        }
    }

    class BareboneHolder extends RecyclerView.ViewHolder {
        public TextView mTxtPieceType;
        public TextView mTxtPieceCode;
        public TextView mTxtPieceName;

        public BareboneHolder(View itemView) {
            super(itemView);
            mTxtPieceType = (TextView) itemView.findViewById(R.id.text_pieceType);
            mTxtPieceCode = (TextView) itemView.findViewById(R.id.text_pieceCode);
            mTxtPieceName = (TextView) itemView.findViewById(R.id.text_pieceName);
        }
    }

    class TitleHolder extends RecyclerView.ViewHolder {
        public TextView mTxtTitle;

        public TitleHolder(View itemView) {
            super(itemView);
            mTxtTitle = (TextView) itemView.findViewById(R.id.text_boxfragTitle);
        }
    }
}
