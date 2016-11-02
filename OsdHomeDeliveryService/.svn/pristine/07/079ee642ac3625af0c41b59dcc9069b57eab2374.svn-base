package com.lenovo.csd.eservice.widget;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.lenovo.csd.eservice.R;

/**
 * @author shengtao
 */

public class CustomChooseDialog extends Dialog {
    private Context context = null;
    private static View customView;
    private Button cancelButton;
    private Button confirmButton;
    private CustomedDialogClickListener mCustomedDialogClickListener;

    public void setCustomedDialogClickListener(CustomedDialogClickListener clickListener){
        mCustomedDialogClickListener=clickListener;
    }

    //private static AnimationDrawable animationDrawable;

    public CustomChooseDialog(Context context) {
        super(context);
        this.context = context;
    }
    public CustomChooseDialog(Context context, int theme,boolean isCancelable) {
        super(context, theme);
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        customView = inflater.inflate(R.layout.layout_customed_defalut_dialog, null);
        this.setCancelable(true);
        this.setContentView(customView);
        this.getWindow().getAttributes().gravity = Gravity.CENTER;
        this.setCancelable(isCancelable);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(customView);
        cancelButton = (Button) customView.findViewById(R.id.custom_default_dialog_button_cancel);
        confirmButton = (Button) customView.findViewById(R.id.custom_default_dialog_button_confirm);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mCustomedDialogClickListener.onCancle();
            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomedDialogClickListener.onConfirm();
            }
        });
    }

    @Override
    public View findViewById(int id) {
        return super.findViewById(id);
    }

    public View getCustomView() {
        return customView;
    }

	/*public static CustomChooseDialog createDialog(Context context) {
        CustomChooseDialog customProgressDialog = null;
		customProgressDialog = new CustomChooseDialog(context,
				R.style.CustomProgressDialog);
		LayoutInflater inflater= LayoutInflater.from(context);
		customView = inflater.inflate(R.layout.layout_customed_defalut_dialog, null);
		customProgressDialog.setCancelable(true);
		customProgressDialog.setContentView(customView);
		customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;

		*//*cancelButton = (Button) customView.findViewById(R.id.custom_default_dialog_button_cancel);
		confirmButton = (Button) customView.findViewById(R.id.custom_default_dialog_button_confirm);
		cancelButton.setOnClickListener(new customedOnClickListener(alertDialog, mActivity));
		confirmButton.setOnClickListener(new customedOnClickListener(alertDialog, mActivity));*//*
		//ImageView imageView = (ImageView) customProgressDialog
		//		.findViewById(R.id.loading_img);
		//animationDrawable = (AnimationDrawable) imageView.getBackground();
		return customProgressDialog;
	}*/

    @Override
    public void show() {
        // TODO Auto-generated method stub
        //animationDrawable.start();
        super.show();
    }

    @Override
    public void dismiss() {
        // TODO Auto-generated method stub
        //animationDrawable.stop();
        super.dismiss();
    }
    public static interface CustomedDialogClickListener{
        public void onConfirm();
        public void onCancle();

    }

}
