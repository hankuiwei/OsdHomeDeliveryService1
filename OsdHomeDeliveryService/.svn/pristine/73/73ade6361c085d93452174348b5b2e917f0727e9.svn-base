package com.lenovo.csd.eservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.entity.base.UpChangeInfo;

import java.util.HashMap;
import java.util.List;

/**
 * Created by hankw on 16-8-20.
 */
public class ChangeRecordAdapter extends RecyclerView.Adapter<ChangeRecordAdapter.ViewHolder> {

    private final String TAG = getClass().getSimpleName();
    private Context mContext;
    private List<UpChangeInfo> list;
    private View mView;
    private UpChangeInfo upChangeInfo;
    private boolean isInitSelect;
   // private CheckBox mRadioBtnSelect;
    // 用于记录每个RadioButton的状态，并保证只可选一个
   private HashMap<String, Boolean> states = new HashMap<String, Boolean>();


    public ChangeRecordAdapter(Context mContext, List<UpChangeInfo> list) {
        this.mContext = mContext;
        this.list = list;


    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private InitOneItemLinstener mInitOneItemLinstener = null;

    //define interface
    public static interface OnRecyclerViewItemClickListener {
            void onItemClick(View view, UpChangeInfo data);
    }

    public static interface InitOneItemLinstener{
        void oneItem(UpChangeInfo udata);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        mView = LayoutInflater.from(mContext).inflate(R.layout.item_change_up,viewGroup,false);
        /*int[] ids = {R.id.Txt_sparePartId,R.id.Txt_sparePartBar,R.id.Txt_SparePartDescribe,
                    R.id.Txt_sparePartClassificat,R.id.Txt_sparePartPriceUp,R.id.RadioBtn_select
                    };*/
        /*//将创建的View注册点击事件
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long l = getItemId(i);
               *//* if (mOnItemClickListener != null){
                    //注意这里使用getTag方法获取数据
                    mOnItemClickListener.onItemClick(v, (UpChangeInfo) v.getTag());
                    // 重置，确保最多只有一项被选中
                    for (String key : states.keySet()) {
                        states.put(key, false);

                    }
                    states.put(String.valueOf(i),mRadioBtnSelect.isChecked());
                    notifyDataSetChanged();
                }*//*
            }
        });*/
       // return new ViewHolder(mView,ids);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
      //  viewHolder.position = i;
        UpChangeInfo mUpChangeInfo = list.get(i);
        //将数据保存在itemView的Tag中，以便点击时进行获取
        viewHolder.itemView.setTag(mUpChangeInfo);
      //  View[] views = viewHolder.getViews();
        if (mUpChangeInfo != null){
           /* TextView mTxtSparePartId = (TextView) views[0];
            TextView mTxtSparePartBar = (TextView) views[1];
            TextView mTxtDescribe = (TextView) views[2];
            TextView mTxtClassificat = (TextView) views[3];
            TextView mTxtPriceUp = (TextView) views[4];
            mRadioBtnSelect = (CheckBox) views[5];*/
            //mRadioBtnSelect.setEnabled(false);
           // ImageView mRadioBtnSelect = (ImageView) views[5];
            int id = mUpChangeInfo.getId();
            String code = mUpChangeInfo.getChangeCode();
            String sn = mUpChangeInfo.getMachineUpSn();
            String desc = mUpChangeInfo.getMachineUpDesc();
            String type = mUpChangeInfo.getChangeType();
            String price = mUpChangeInfo.getMachinePrice();

            viewHolder.mTxtSparePartId.setText(code);
            viewHolder.mTxtSparePartBar.setText(sn);
            viewHolder.mTxtDescribe.setText(desc);
            viewHolder.mTxtClassificat.setText(type);
            viewHolder.mTxtPriceUp.setText(price);

            viewHolder.mTxtSparePartId.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                    // 将文本内容放到系统剪贴板里。
                    cm.setText(viewHolder.mTxtSparePartId.getText().toString());
                    Toast.makeText(mContext, "复制成功", Toast.LENGTH_LONG).show();
                    return false;
                }
            });

            /*mRadioBtnSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext,"" +i,Toast.LENGTH_LONG).show();
                }
            });*/
            /*mRadioBtnSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });*/

            if (i == 0 && !isInitSelect){
               // upChangeInfo = new UpChangeInfo(id,code,desc,sn,type,price);
                if (mInitOneItemLinstener != null){
                    mInitOneItemLinstener.oneItem(mUpChangeInfo);
                }
                viewHolder.mRadioBtnSelect.setChecked(true);
                boolean checked = viewHolder.mRadioBtnSelect.isChecked();
                states.put(String.valueOf(0),checked);
                isInitSelect = true;
              //  notifyDataSetChanged();
            }else {
                this.upChangeInfo = null;
               /* for (String key : states.keySet()) {
                    states.put(key, false);
                }*/
                viewHolder.mRadioBtnSelect.setChecked(false);
                boolean checked = viewHolder.mRadioBtnSelect.isChecked();
                //states.put(String.valueOf(i),checked);
               // notifyDataSetChanged();
            }

           View view = viewHolder.getView();

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null){
                        //注意这里使用getTag方法获取数据
                        mOnItemClickListener.onItemClick(v, (UpChangeInfo) v.getTag());
                        // 重置，确保最多只有一项被选中
                        for (String key : states.keySet()) {
                            states.put(key, false);
                        }
                        //mRadioBtnSelect.setChecked(true);
                        viewHolder.mRadioBtnSelect.setChecked(true);
                        boolean checked = viewHolder.mRadioBtnSelect.isChecked();
                        states.put(String.valueOf(i),checked);
                        notifyDataSetChanged();

                    }
                }
            });

            boolean res = false;
            // boolean state = states.get(String.valueOf(position));
            if (states.get(String.valueOf(i)) == null || states.get(String.valueOf(i)) == false) {
                // if (state == false) {
                res = false;
                states.put(String.valueOf(i), false);
            } else {
                res = true;
            }
            viewHolder.mRadioBtnSelect.setChecked(res);
        }

    }




    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {

        this.mOnItemClickListener = listener;
    }

    public void setOneItem(InitOneItemLinstener linstener){
        this.mInitOneItemLinstener = linstener;
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTxtSparePartId;
        TextView mTxtSparePartBar;
        TextView mTxtDescribe;
        TextView mTxtClassificat;
        TextView mTxtPriceUp;
        CheckBox mRadioBtnSelect;
         View v;
        /*int[] ids = {R.id.Txt_sparePartId,R.id.Txt_sparePartBar,R.id.Txt_SparePartDescribe,
                    R.id.Txt_sparePartClassificat,R.id.Txt_sparePartPriceUp,R.id.RadioBtn_select
                    };*/
        public ViewHolder(View itemView){
            super(itemView);
            v = itemView;
            mTxtSparePartId = (TextView) itemView.findViewById(R.id.Txt_sparePartId);
            mTxtSparePartBar = (TextView) itemView.findViewById(R.id.Txt_sparePartBar);
            mTxtDescribe = (TextView) itemView.findViewById(R.id.Txt_SparePartDescribe);
            mTxtClassificat = (TextView) itemView.findViewById(R.id.Txt_sparePartClassificat);
            mTxtPriceUp = (TextView) itemView.findViewById(R.id.Txt_sparePartPriceUp);
            mRadioBtnSelect = (CheckBox) itemView.findViewById(R.id.RadioBtn_select);

        }
 /*       View[] views;
        public int position;
        private RadioButton radioButton;
        private View v;

        public ViewHolder(View itemView,int[] ids) {
            super(itemView);
            views = new View[ids.length];
            v = itemView;
            for (int i = 0; i < ids.length; i++) {
                views[i] = itemView.findViewById(ids[i]);
             }
           // radioButton  = (RadioButton) views[5];
           // itemView.setOnClickListener(this);


        }

        public View[] getViews(){
            return views;
        }

        public View getView(){
            return v;
        }

        *//*@Override
        public void onClick(View v) {

        }*/

        public View getView(){
            return v;
        }
    }
}
