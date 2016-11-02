package com.lenovo.csd.eservice.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.util.Utils;

public class QRCodeFragment extends DialogFragment {

    private String qrUrl = "";//二维码地址
    private ImageView mImageQR;
    //    private TextView mTxtClose;
    public static final String QR_URL = "qr_url";

    public QRCodeFragment() {

    }

    public static QRCodeFragment newInstance(String qrUrl) {
        QRCodeFragment fragment = new QRCodeFragment();
        Bundle args = new Bundle();
        args.putString(QR_URL, qrUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            qrUrl = getArguments().getString(QR_URL);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View layout = inflater.inflate(R.layout.fragment_qrcode, null);
        mImageQR = (ImageView) layout.findViewById(R.id.img_QRcode);
        Utils.loadImageWithGlide(getActivity(), qrUrl, mImageQR, R.drawable.default_load_img);
        builder.setView(layout);
        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();
        Utils.loadImageWithGlide(getActivity(), qrUrl, mImageQR, R.drawable.default_load_img);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
