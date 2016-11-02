package com.lenovo.csd.eservice.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.entity.base.ContactData;
import com.lenovo.csd.eservice.entity.base.OrderDetailAllData;
import com.lenovo.csd.eservice.entity.base.TypeData;
import com.lenovo.csd.eservice.util.BaiduNavigation;
import com.lenovo.csd.eservice.util.Utils;

import java.util.ArrayList;


/**
 * Created by 彤 on 2016/10/12.
 */
public class CustomInfoMoreAdapter extends RecyclerView.Adapter {

    private Activity context;
    private ArrayList<TypeData> datas;
    private LayoutInflater inflater;

    public CustomInfoMoreAdapter(Activity context, ArrayList<TypeData> datas) {
        this.context = context;
        this.datas = datas;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return new CustomInfoHolder(inflater.inflate(R.layout.item_morecustomeinfo_single, parent, false));
            case 1:
                return new TitleViewHolder(inflater.inflate(R.layout.item_title_boxfrag, parent, false));
            case 2:
                return new ContactRecordHolder(inflater.inflate(R.layout.item_contactrecord_morecustomfrag, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TypeData data = datas.get(position);
        if (holder instanceof CustomInfoHolder) {
            final String phoneNumber = ((OrderDetailAllData.OrderDetailBean) data).getContact_phone();
            final String addr = ((OrderDetailAllData.OrderDetailBean) data).getCustomer_address();
            ((CustomInfoHolder) holder).mTxtCustomName.setText(TextUtils.isEmpty(((OrderDetailAllData.OrderDetailBean) data).getCustomer_name()) ? "无" : ((OrderDetailAllData.OrderDetailBean) data).getCustomer_name());
            ((CustomInfoHolder) holder).mTxtCustomPhone.setText(TextUtils.isEmpty(phoneNumber) ? "无" : phoneNumber);
            ((CustomInfoHolder) holder).mTxtCustomAddr.setText(TextUtils.isEmpty(addr) ? "无" : addr);
            ((CustomInfoHolder) holder).mTxtCustomLevel.setText(TextUtils.isEmpty(((OrderDetailAllData.OrderDetailBean) data).getCustomer_level()) ? "无" : ((OrderDetailAllData.OrderDetailBean) data).getCustomer_level());
            if (TextUtils.isEmpty(((OrderDetailAllData.OrderDetailBean) data).getCompany_name())) {
                ((CustomInfoHolder) holder).mTableCompanyName.setVisibility(View.GONE);
            } else {
                ((CustomInfoHolder) holder).mTableCompanyName.setVisibility(View.VISIBLE);
                ((CustomInfoHolder) holder).mTxtCustomCompany.setText(((OrderDetailAllData.OrderDetailBean) data).getCompany_name());
            }
            ((CustomInfoHolder) holder).mTxtCustomPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(phoneNumber)) {
                        Utils.callNumber(context, phoneNumber);
                    }
                }
            });
            ((CustomInfoHolder) holder).mTxtCustomAddr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(addr)) {
                        new BaiduNavigation(context, context, addr);
                    }
                }
            });

        } else if (holder instanceof TitleViewHolder) {
            ((TitleViewHolder) holder).mTitle.setText(((ContactData.MoreCustomTitle) data).getTitle());
        } else if (holder instanceof ContactRecordHolder) {
            ((ContactRecordHolder) holder).mTxtRecordTime.setText(TextUtils.isEmpty(((ContactData.ContactItemData) data).getCreate_time()) ? "无" : ((ContactData.ContactItemData) data).getCreate_time());
            ((ContactRecordHolder) holder).mTxtRecordChannel.setText(TextUtils.isEmpty(((ContactData.ContactItemData) data).getConsult_type()) ? "无" : ((ContactData.ContactItemData) data).getConsult_type());
            ((ContactRecordHolder) holder).mTxtRecordContent.setText(TextUtils.isEmpty(((ContactData.ContactItemData) data).getLast_sln()) ? "无" : ((ContactData.ContactItemData) data).getLast_sln());
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public int getItemViewType(int position) {

        TypeData data = datas.get(position);
        return data.getType();
    }

    public class CustomInfoHolder extends RecyclerView.ViewHolder {
        public TextView mTxtCustomName;
        public TextView mTxtCustomPhone;
        public LinearLayout mLinearAddr;
        public TextView mTxtCustomAddr;
        public TextView mTxtCustomLevel;
        public TextView mTxtCustomCompany;
        public TableRow mTableCompanyName;

        public CustomInfoHolder(View itemView) {
            super(itemView);
            mTxtCustomName = (TextView) itemView.findViewById(R.id.text_consumerName);
            mTxtCustomPhone = (TextView) itemView.findViewById(R.id.text_consumerPhone);
            mLinearAddr = (LinearLayout) itemView.findViewById(R.id.linear_customerAdd);
            mTxtCustomAddr = (TextView) itemView.findViewById(R.id.text_consumerAddress);
            mTxtCustomLevel = (TextView) itemView.findViewById(R.id.text_consumerLevel);
            mTxtCustomCompany = (TextView) itemView.findViewById(R.id.text_consumerCompanyName);
            mTableCompanyName = (TableRow) itemView.findViewById(R.id.tableCustomCompanyName);
        }
    }

    public class TitleViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitle;

        public TitleViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.text_boxfragTitle);
        }
    }

    public class ContactRecordHolder extends RecyclerView.ViewHolder {
        public TextView mTxtRecordTime;
        public TextView mTxtRecordChannel;
        public TextView mTxtRecordContent;

        public ContactRecordHolder(View itemView) {
            super(itemView);
            mTxtRecordTime = (TextView) itemView.findViewById(R.id.text_recordTime);
            mTxtRecordChannel = (TextView) itemView.findViewById(R.id.text_recordChannel);
            mTxtRecordContent = (TextView) itemView.findViewById(R.id.text_contactRecord);
        }
    }
}
