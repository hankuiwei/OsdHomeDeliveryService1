package com.lenovo.csd.eservice.widget;


import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;

import com.lenovo.csd.eservice.R;

/**
 * 
 * @author shengtao
 * 
 */

public class CustomProgressDialog extends Dialog {
	private Context context = null;
	//private static AnimationDrawable animationDrawable;

	public CustomProgressDialog(Context context) {
		super(context);
		this.context = context;
	}

	public CustomProgressDialog(Context context, int theme) {
		super(context, theme);
	}

	public static CustomProgressDialog createDialog(Context context) {
		CustomProgressDialog customProgressDialog = null;
		customProgressDialog = new CustomProgressDialog(context,
				R.style.CustomProgressDialog);
		customProgressDialog.setCancelable(true);
		customProgressDialog.setContentView(R.layout.layout_loading);
		customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;

		//ImageView imageView = (ImageView) customProgressDialog
		//		.findViewById(R.id.loading_img);
		//animationDrawable = (AnimationDrawable) imageView.getBackground();
		return customProgressDialog;
	}

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
}
