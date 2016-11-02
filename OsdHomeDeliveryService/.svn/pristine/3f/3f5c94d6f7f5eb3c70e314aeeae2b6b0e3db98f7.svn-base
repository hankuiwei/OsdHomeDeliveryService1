package com.lenovo.csd.eservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.entity.base.ChangeBoxsInfo;

import java.util.List;

/**
 * Created by hankw on 16-8-20.
 */
public class PackingListAdapter extends RecyclerView.Adapter<PackingListAdapter.ViewHolder> implements View.OnClickListener {

    private final String TAG = getClass().getSimpleName();
    private Context mContext;
    private List<ChangeBoxsInfo.BoxList> list;
    private View mView;

    public PackingListAdapter(Context mContext, List<ChangeBoxsInfo.BoxList> list) {
        this.mContext = mContext;
        this.list = list;
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    //define interface
    public static interface OnRecyclerViewItemClickListener {
            void onItemClick(View view, ChangeBoxsInfo.BoxList data);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        mView = LayoutInflater.from(mContext).inflate(R.layout.item_change_up,viewGroup,false);
        int[] ids = {R.id.Txt_sparePartId,R.id.Txt_sparePartBar,R.id.Txt_SparePartDescribe,
                    R.id.Txt_sparePartClassificat,R.id.Txt_sparePartPriceUp,R.id.RadioBtn_select,
                    R.id.Lin_ChangeGroup,R.id.Lin_changeLine
                    };
        //将创建的View注册点击事件
        mView.setOnClickListener(this);
        return new ViewHolder(mView,ids);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        ChangeBoxsInfo.BoxList mBoxInfo = list.get(i);
        //将数据保存在itemView的Tag中，以便点击时进行获取
        viewHolder.itemView.setTag(mBoxInfo);
        View[] views = viewHolder.getViews();
        if (mBoxInfo != null){
            TextView mTxtSparePartId = (TextView) views[0];
            TextView mTxtSparePartBar = (TextView) views[1];
            TextView mTxtDescribe = (TextView) views[2];
            TextView mTxtClassificat = (TextView) views[3];
            TextView mTxtPriceUp = (TextView) views[4];
           //RadioButton mRadioBtnSelect = (RadioButton) views[5];
           CheckBox mRadioBtnSelect = (CheckBox) views[5];
           // ImageView mRadioBtnSelect = (ImageView) views[5];
            LinearLayout mChangeGroup = (LinearLayout) views[6];
            LinearLayout mchangeLine = (LinearLayout) views[7];

            mTxtSparePartId.setText(mBoxInfo.getCode());
            mTxtSparePartBar.setText(mBoxInfo.getSn());
            mTxtDescribe.setText(mBoxInfo.getDesc());
            mTxtClassificat.setText(mBoxInfo.getType());
            mTxtPriceUp.setText(mBoxInfo.getPrice());


            mRadioBtnSelect.setVisibility(View.GONE);
            mChangeGroup.setPadding(0,mContext.getResources().getDimensionPixelSize(R.dimen.layout_25dp),0,0);
            if ( i != (getItemCount()-1)){
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mchangeLine.getLayoutParams();
                layoutParams.setMargins(0,mContext.getResources().getDimensionPixelSize(R.dimen.layout_20dp),0,0);
                mchangeLine.setLayoutParams(layoutParams);
            } else {
                mchangeLine.setVisibility(View.GONE);
            }


        }
    }


    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null){
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (ChangeBoxsInfo.BoxList) v.getTag());
        }

    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {

        this.mOnItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View[] views;
        public ViewHolder(View itemView,int[] ids) {
            super(itemView);
            views = new View[ids.length];
            for (int i = 0; i < ids.length; i++) {
                views[i] = itemView.findViewById(ids[i]);
             }
        }

        public View[] getViews(){
            return views;
        }
    }
}
