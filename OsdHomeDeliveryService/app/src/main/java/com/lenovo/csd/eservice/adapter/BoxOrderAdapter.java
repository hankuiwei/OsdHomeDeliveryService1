package com.lenovo.csd.eservice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.entity.base.BoxOrderData;

import java.util.ArrayList;

/**
 * Created by 彤 on 2016/8/20.
 */
public class BoxOrderAdapter extends BaseAdapter {
    private ArrayList<BoxOrderData.BoxBeanItem> datas;
    private Context context;
    private LayoutInflater inflater;

    public BoxOrderAdapter(Context context, ArrayList<BoxOrderData.BoxBeanItem> datas) {
        this.context = context;
        this.datas = datas;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.item_boxorder_listview, null, false);
            holder.mTxtPieceType = (TextView) view.findViewById(R.id.text_pieceType);
            holder.mTxtPieceCode = (TextView) view.findViewById(R.id.text_pieceCode);
            holder.mTxtPieceName = (TextView) view.findViewById(R.id.text_pieceName);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        //赋值
        BoxOrderData.BoxBeanItem bean = datas.get(i);
        if (bean != null) {
            holder.mTxtPieceType.setText(bean.getMaterial_category());
            holder.mTxtPieceCode.setText(bean.getMaterial_code());
            holder.mTxtPieceName.setText(bean.getMaterial_name());
        }

        return view;
    }

    class ViewHolder {
        public TextView mTxtPieceType;
        public TextView mTxtPieceCode;
        public TextView mTxtPieceName;
    }

    public void refreshDatas(ArrayList<BoxOrderData.BoxBeanItem> datas) {
        this.datas.clear();
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }
}
