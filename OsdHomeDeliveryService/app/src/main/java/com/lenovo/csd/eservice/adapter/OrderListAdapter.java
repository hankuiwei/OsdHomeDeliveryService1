package com.lenovo.csd.eservice.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.activity.OperateOrderActivity;
import com.lenovo.csd.eservice.entity.base.Label;
import com.lenovo.csd.eservice.entity.base.Orders;
import com.lenovo.csd.eservice.util.BaiduNavigation;
import com.lenovo.csd.eservice.util.Utils;

import java.util.List;

/**
 * Created by hankw on 16-8-3.
 */
public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder>{

    private final String TAG = getClass().getSimpleName();

    private List<Orders> mlist;
    private Context mcontext;
    private boolean isShowOrderInfo;
    private String mOrderState;
    private View view;
    private Activity mactivity;
    private final int BACKFLAG= 1683;
    private String mcallTel;
    public OrderListAdapter(Activity activity,List<Orders> list , Context mcontext,String state) {
        this.mlist = list;
        //mlist.addAll(list);
        //this.mlist = list;
        this.mcontext = mcontext;
        this.mOrderState = state;
        this.mactivity = activity;
        //notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_page_orders,viewGroup,false);
        int ids[] = {
                R.id.txtId,R.id.txtPreTime,R.id.txtState,  R.id.txtType,R.id.imgArrow,R.id.linearLebel,0
               /* R.id.includePulbicTable*/,R.id.txTName,R.id.tableRowTel,R.id.relativePhones,R.id.tableRowAddress,
                R.id.txtAddress,R.id.imgNavigation,R.id.tableRowProduct,R.id.txtProduct,R.id.tableRowFailure,
                R.id.txtFailure,R.id.linear_customerAdd,R.id.tableRowName,R.id.tableRowFailure_topLine,R.id.tableRowAddress_topLine,
                R.id.txtPhones,R.id.view_orderTable,R.id.Order_item
        };

        return new ViewHolder(view,ids);
    }
    String orderid;
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {

         Orders mOrders = mlist.get(i);
        orderid =  mOrders.getOrder_id();
        if (mOrders == null)
            return;
        View[] views = viewHolder.getItemView();
        TextView mTxtId = (TextView) views[0];
        TextView mTxtPreTime = (TextView) views[1];
        TextView mTxtTate = (TextView) views[2];
        TextView mTxtType = (TextView) views[3];
        ImageView mImgRrrow = (ImageView) views[4];
        LinearLayout mLinearLebel = (LinearLayout) views[5];
        View mViewTable = views[6];
        TextView mTxtName = (TextView) views[7];
        TableRow mtabRowPhone = (TableRow) views[8];
        LinearLayout mRelPhones = (LinearLayout) views[9];
        TableRow mtableRowAddress = (TableRow) views[10];
        TextView mTxtAddress = (TextView) views[11];
        ImageView mImgNavigation = (ImageView) views[12];
        TableRow mtableRowProduct = (TableRow) views[13];
        TextView mTxtProduct = (TextView) views[14];
        TableRow mtableRowFailure = (TableRow) views[15];
        TextView mtxtFailure = (TextView) views[16];
        LinearLayout mcustomerAdd = (LinearLayout) views[17];
        TableRow mtableRowName = (TableRow) views[18];
        LinearLayout mFailureTopLine = (LinearLayout) views[19];
        LinearLayout mAddresstopLine = (LinearLayout) views[20];
        TextView mTxtPhone = (TextView) views[21];
        TableLayout mOrderT = (TableLayout) views[22];
        LinearLayout mOrderItem = (LinearLayout) views[23];
        //mOrderT.setAlpha(1.0f);
        //mOrderItem.setAlpha(1.0f);
        mTxtId.setText(mOrders.getOrder_id());
        mTxtId.requestFocus();
        mTxtId.setFocusable(true);
        String time = "";
        if ("1".equals(mOrderState)){
             time = mOrders.getServiceCompleteTime();
            if ("".equals(time)){
                time = "暂无完成时间";
            }
        }else {
            time = mOrders.getPre_time();
            if ("".equals(time)){
                time = "暂无预约时间";
            }
        }
        mTxtPreTime.setText("("+time+")");
        mTxtPreTime.setFocusable(true);
        if (!"1".equals(mOrderState)){
            mTxtTate.setVisibility(View.VISIBLE);
            mTxtTate.setText(mOrders.getOrder_state_name());
        } else {
            mTxtTate.setVisibility(View.INVISIBLE);
        }

        mTxtType.setText(mOrders.getOrder_type_name());
        TableRow[] tableRows = {mtableRowName,mtabRowPhone,mtableRowAddress,mtableRowProduct,mtableRowFailure};
        if (mLinearLebel != null) {
            mLinearLebel.removeAllViews();
        }
        //String[] labels = mOrders.getLabels();
        List<Label> labels = mOrders.getLabels();
        //if (labels != null && labels.length > 0) {
        if (labels != null && labels.size() > 0) {
            mLinearLebel.setVisibility(View.VISIBLE);

            int bgcolor[] = {R.color.color_ED9794, R.color.color_FFBA00, R.color.color_98D89B, R.color.color_7ED5DD, R.color.color_B6C7EE};
           /* int colorFlag = 0;
            for (int l = 0; l < labels.length; l++) {
                if (colorFlag == 4) {
                    colorFlag = 0;
                }
                TextView labelText = new TextView(mcontext);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(0,0,mcontext.getResources().getDimensionPixelSize(R.dimen.layout_5dp),0);
                labelText.setLayoutParams(lp);
                int padd = mcontext.getResources().getDimensionPixelSize(R.dimen.layout_5dp);
                labelText.setPadding(padd,padd,padd,padd);
                int textSize = Utils.px2dip(mcontext,22);
                labelText.setTextSize(textSize);
                int strokeWidth = 5; // 3dp 边框宽度
                int roundRadius = 7; // 8dp 圆角半径
                int strokeColor = Color.parseColor("#2E3135");//边框颜色
                int fillColor = Color.parseColor("#DFDFE0");//内部填充颜色

                GradientDrawable gd = new GradientDrawable();//创建drawable
                gd.setColor(mcontext.getResources().getColor(bgcolor[colorFlag]));
                //Utils.setBackground(gd,mcontext.getResources().getDrawable(bgcolor[colorFlag]));
                gd.setCornerRadius(roundRadius);
                labelText.setBackgroundDrawable(gd);
                labelText.setText(labels[l]);
                labelText.setGravity(Gravity.CENTER);
                labelText.setTextColor(mcontext.getResources().getColor(R.color.white));
                mLinearLebel.addView(labelText);
                colorFlag ++;
            }*/

            for (Label label : labels){
                String labeltext = "";
                labeltext = label.getName();
                if (labeltext == null || "".equals(labeltext)){
                    break;
                }
                TextView labelText = new TextView(mcontext);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(0,0,mcontext.getResources().getDimensionPixelSize(R.dimen.layout_5dp),0);
                labelText.setLayoutParams(lp);
                int padd = mcontext.getResources().getDimensionPixelSize(R.dimen.layout_5dp);
                labelText.setPadding(padd,padd,padd,padd);
                int textSize = Utils.px2dip(mcontext,22);
                //labelText.setTextSize(textSize);
                labelText.setTextSize(TypedValue.COMPLEX_UNIT_SP,8.0f);
                int strokeWidth = 5; // 3dp 边框宽度
                int roundRadius = 7; // 8dp 圆角半径
                int strokeColor = Color.parseColor("#2E3135");//边框颜色
                int fillColor = Color.parseColor("#DFDFE0");//内部填充颜色*//*

                GradientDrawable gd = new GradientDrawable();//创建drawable
                String bg = label.getBg();
                int colorid = 0;
                  switch (bg){
                      case "r":
                        colorid = R.color.color_ED9794;
                          break;
                      case "y":
                        colorid = R.color.color_FFBA00;
                          break;
                      case "b":
                        colorid = R.color.color_B6C7EE;
                          break;


                  }
                if (colorid != 0){
                    gd.setColor(mcontext.getResources().getColor(colorid));
                }


                //Utils.setBackground(gd,mcontext.getResources().getDrawable(bgcolor[colorFlag]));
                gd.setCornerRadius(roundRadius);
                labelText.setBackgroundDrawable(gd);
               // labelText.setText(labels[l]);
                labelText.setText(label.getName());
                labelText.setGravity(Gravity.CENTER);
                labelText.setTextColor(mcontext.getResources().getColor(R.color.white));
                mLinearLebel.addView(labelText);
            }
        } else {
            mLinearLebel.setVisibility(View.GONE);
        }


        String name = mOrders.getCustomer_name();
        if (name != null) {
            mtableRowName.setVisibility(View.VISIBLE);
            mTxtName.setText(name);
        } else {
            mtableRowName.setVisibility(View.GONE);

        }

        //String[] tel = mOrders.getCustomer_phones();
        final String tel = mOrders.getContactPhone();
        /*if(mRelPhones != null){
            mRelPhones.removeAllViews();
        }*/
        if (tel != null  && !"".equals(tel)&& ! "1".equals(mOrderState)) {


            mtabRowPhone.setVisibility(View.VISIBLE);
            mRelPhones.setVisibility(View.VISIBLE);
            mAddresstopLine.setVisibility(View.VISIBLE);
            //for (int tel_num = 0; tel_num < 1; tel_num ++){
                /*LinearLayout tel_Lin = new LinearLayout(mcontext);

                tel_Lin.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                tel_Lin.setOrientation(LinearLayout.HORIZONTAL);
                tel_Lin.setGravity(Gravity.CENTER_VERTICAL);
                tel_Lin.setPadding(mcontext.getResources().getDimensionPixelSize(R.dimen.layout_10dp),mcontext.getResources().getDimensionPixelSize(R.dimen.layout_13dp),0,mcontext.getResources().getDimensionPixelSize(R.dimen.layout_13dp));
                tel_Lin.setBackgroundResource(R.drawable.selector_bg_change_me);
                ImageView callImg = new ImageView(mcontext);
                callImg.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,0.5f));
                callImg.setImageResource(R.drawable.icon_orders_unfinished_phone);
                *//*callImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });*//*
                TextView mTxtTel = new TextView(mcontext);
                    mTxtTel.setLayoutParams(new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT,7.0f));
                    //final String num = tel[tel_num];
                    final String num = tel;
                    mTxtTel.setText(num);
                tel_Lin.addView(mTxtTel);
                tel_Lin.addView(callImg);
                tel_Lin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utils.callNumber(mcontext,num);
                    }
                });
                 mRelPhones.addView(tel_Lin);
                 */
           // }
            mcallTel = tel;
            mTxtPhone.setText(tel);
            mRelPhones.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.callNumber(mcontext,tel);
                }
            });

        } else {
            mtabRowPhone.setVisibility(View.GONE);
            mRelPhones.setVisibility(View.GONE);
            mAddresstopLine.setVisibility(View.GONE);
        }

        final String address = mOrders.getCustomer_address();
        if (address != null) {
            mtableRowAddress.setVisibility(View.VISIBLE);
            mTxtAddress.setText(address);
        } else {
            mtableRowAddress.setVisibility(View.GONE);
        }
       final String baidu_location = mOrders.getCustomer_baidu_location();
        mcustomerAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int netState =  Utils.checkInternetStatus(mactivity);
                if (netState == 0) {
                    Toast.makeText(mactivity,"网络不可用,请连接网络后重试",Toast.LENGTH_SHORT).show();
                    return;
                }

                double latitude = 0.0;
                double longitude = 0.0;
                if (baidu_location !=null && !"".equals(baidu_location)){
                        String lon = baidu_location.substring(0,baidu_location.indexOf(","));
                        String lat = baidu_location.substring(baidu_location.indexOf(","));
                        longitude = Double.parseDouble(lat);
                        latitude  = Double.parseDouble(lon);
                }
               /* new BaiduNavigation(mactivity,longitude,latitude,address);*/
              new BaiduNavigation(mcontext,mactivity,address);

            }
        });

        String product = mOrders.getProduct_sn();
        String productType = mOrders.getProduct_type();
        if (product != null && productType != null) {
            mtableRowProduct.setVisibility(View.VISIBLE);
            mFailureTopLine.setVisibility(View.VISIBLE);
            mTxtProduct.setText(product+" "+"【"+productType+"】");
        } else {
            mtableRowProduct.setVisibility(View.GONE);
            mFailureTopLine.setVisibility(View.GONE);
        }
        String failure = mOrders.getFailure_description();
        if (failure != null) {
            mtableRowFailure.setVisibility(View.VISIBLE);
            mtxtFailure.setText(failure);
        } else {
            mtableRowFailure.setVisibility(View.GONE);

        }
        int colorindex = 0;
        int[] tablecolors ={R.color.gray_F8F8F8,R.color.white};
        for (int s = 0; s < tableRows.length; s++){
                if (colorindex ==2){
                    colorindex=0;
                }
            if (tableRows[s].getVisibility() == View.VISIBLE){
                //tableRows[s].setBackgroundResource(tablecolors[colorindex]);
                Utils.setBackground(tableRows[s],mcontext.getResources().getDrawable(tablecolors[colorindex]));
                colorindex++;
            }
        }

    }

    @Override
    public int getItemCount() {
        if (mlist == null){
            return 0;
        }
        return mlist.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        public int position;
        private View[] v;
        private TableRow mTRName;
        private TableRow mTRPhone;
        private TableRow mTRAddress;
        private TableRow mTRProduct;
        private TableRow mTRFailure;
        private TableLayout mOrderTab;
        private LinearLayout mOrdItem;
        //TableRow[] tableRows = {mtableRowName,mtabRowPhone,mtableRowAddress,mtableRowProduct,mtableRowFailure};
        public ViewHolder(final View itemView, int[] ids) {
            super(itemView);

             v = new View[ids.length];
            for (int i = 0; i < ids.length; i++ ) {
                v [i] = itemView.findViewById(ids[i] );
            }
            mTRName = (TableRow) itemView.findViewById(R.id.tableRowName);
            mTRPhone = (TableRow) itemView.findViewById(R.id.tableRowTel);
            mTRAddress = (TableRow) itemView.findViewById(R.id.tableRowAddress);
            mTRProduct = (TableRow) itemView.findViewById(R.id.tableRowProduct);
            mTRFailure = (TableRow) itemView.findViewById(R.id.tableRowFailure);
            //mOrderTab = (TableLayout) itemView.findViewById(R.id.view_orderTable);
            mOrdItem = (LinearLayout) itemView.findViewById(R.id.Order_item);
            final TableRow[] tabRows = {mTRName,mTRPhone,mTRAddress,mTRProduct,mTRFailure};
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // mOrderTab.setAlpha(0.9f);

                   int id =  ViewHolder.super.getLayoutPosition();
                    String orid = mlist.get(id).getOrder_id();

                   /* for (int s = 0; s < tabRows.length; s++){
                        if (tabRows[s].getVisibility() == View.VISIBLE){
                            //tableRows[s].setBackgroundResource(tablecolors[colorindex]);
                            Utils.setBackground(tabRows[s],mcontext.getResources().getDrawable(R.drawable.shape_4dpcorner_change_me_graypressedbg));
                        }
                    }*/
                    OperateOrderActivity.openOrderDetailForResult(mactivity,orid,false,BACKFLAG,mOrderState);
                }
            });
        }
        public View[] getItemView() {
            return v;
        }
    }



    /**
     * 刷新数据
     *
     * @param list 待加载的新数据
     */
    public void refreshData(List<Orders> list) {
        if (this.mlist == null)
            return;
        this.mlist.clear();
        this.mlist.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     */
    public void addData(List<Orders> list) {
        if (this.mlist == null)
            return;
        this.mlist.addAll(list);
        notifyDataSetChanged();
    }

}
