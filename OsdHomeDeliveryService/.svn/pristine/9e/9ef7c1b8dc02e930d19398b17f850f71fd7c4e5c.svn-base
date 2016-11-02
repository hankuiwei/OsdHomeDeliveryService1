package com.lenovo.csd.eservice.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lenovo.csd.eservice.R;

import java.util.ArrayList;

public class HintFragment extends DialogFragment implements View.OnClickListener {
    private static final String TITLE = "title";
    private static final String CANCEL_TXT = "cancel";
    private static final String CONFIRM_TXT = "confirm";
    private static final String MESSAGE = "error_msg";
    private static final String ITEM_CONTENTS = "content_items";
    private static final String CANCEL_ACTION = "cancel_action";
    private static final String CONFIRM_ACTION = "confirm_action";

    private String mTitle;
    private String mErrorMsg;
    private ArrayList<String> items;
    private String mCancelTxt;
    private String mConfirmTxt;
    public int ACTION_CANCEL;
    public int ACTION_CONFIRM;
    private TextView mTextTitle;
    private TextView mErrorTxt;
    private LinearLayout mLinearContent;
    private LinearLayout mLinearBtns;
    private Button mBtnCancel;
    private Button mBtnConfirm;

    private OnFragmentInteractionListener mListener;

    public HintFragment() {

    }

    public static HintFragment newInstance(int cancelAction, int confirmAction, String title, String errorMsg, ArrayList<String> items, String cancelTxt, String confirmTxt) {
        HintFragment fragment = new HintFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(MESSAGE, errorMsg);
        args.putString(CANCEL_TXT, cancelTxt);
        args.putString(CONFIRM_TXT, confirmTxt);
        args.putStringArrayList(ITEM_CONTENTS, items);
        args.putInt(CANCEL_ACTION, cancelAction);
        args.putInt(CONFIRM_ACTION, confirmAction);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(TITLE);
            mErrorMsg = getArguments().getString(MESSAGE);
            items = getArguments().getStringArrayList(ITEM_CONTENTS);
            mCancelTxt = getArguments().getString(CANCEL_TXT);
            mConfirmTxt = getArguments().getString(CONFIRM_TXT);
            ACTION_CANCEL = getArguments().getInt(CANCEL_ACTION);
            ACTION_CONFIRM = getArguments().getInt(CONFIRM_ACTION);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout((int)((float)dm.widthPixels * 3 / 4), getDialog().getWindow().getAttributes().height);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mListener = (OnFragmentInteractionListener) getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View layout = inflater.inflate(R.layout.fragment_hint, null);
        mTextTitle = (TextView) layout.findViewById(R.id.text_hintTitle);
        mErrorTxt = (TextView) layout.findViewById(R.id.text_errorMsg);
        mLinearContent = (LinearLayout) layout.findViewById(R.id.linear_hintContent);
        mLinearBtns = (LinearLayout) layout.findViewById(R.id.linear_hintBtns);
        mBtnCancel = (Button) layout.findViewById(R.id.btn_cancelAction);
        mBtnConfirm = (Button) layout.findViewById(R.id.btn_confirmAction);
        if (mTitle == null || TextUtils.isEmpty(mTitle)) {//么有标题隐藏
            mTextTitle.setVisibility(View.GONE);
        } else {
            mTextTitle.setText(mTitle);
        }
        if (mErrorMsg == null || TextUtils.isEmpty(mErrorMsg)) {
            mErrorTxt.setVisibility(View.GONE);
        } else {
            mErrorTxt.setText(mErrorMsg);
        }
        if (items == null || items.size() == 0) {
            mLinearContent.setVisibility(View.GONE);
        } else {
            addContent(items);
        }
        mBtnCancel.setText(mCancelTxt);
        mBtnConfirm.setText(mConfirmTxt);
        builder.setView(layout);
        mBtnCancel.setOnClickListener(this);
        mBtnConfirm.setOnClickListener(this);
        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();
//        DisplayMetrics metrics = new DisplayMetrics();
//        WindowManager windowManager = getActivity().getWindowManager();
//        windowManager.getDefaultDisplay().getMetrics(metrics);
//        getDialog().getWindow().setLayout(metrics.widthPixels - 2 * ((int) getResources().getDimension(R.dimen.layout_65dp)), getDialog().getWindow().getAttributes().height);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancelAction:
                dismiss();
                mListener.onFragmentInteraction(ACTION_CANCEL);
                break;
            case R.id.btn_confirmAction:
                dismiss();
                mListener.onFragmentInteraction(ACTION_CONFIRM);
                break;
        }
    }

    public void addContent(ArrayList<String> items) {
        for (String item : items) {
            TextView errorMsg = new TextView(getActivity());
//            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            errorMsg.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            errorMsg.setTextColor(getResources().getColor(R.color.gray_999999));
            errorMsg.setGravity(Gravity.START);
            errorMsg.setPadding(0, 10, 0, 10);
//            errorMsg.setLineSpacing(0f, 0.5f);
            errorMsg.setTextSize(15f);
            errorMsg.setText(item);
            mLinearContent.addView(errorMsg);
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(int Action);
    }
}
