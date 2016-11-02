package com.lenovo.csd.eservice.http.provider;

import android.content.Context;

import com.lenovo.csd.eservice.ServiceApplication;
import com.lenovo.csd.eservice.http.provider.base.IImageLoaderProvider;
import com.lenovo.csd.eservice.http.request.ImageRequest;
import com.squareup.picasso.Picasso;


/**
 * Created by shengtao
 * Date:2016/7/21
 * Time:11:03
 */
public class PicassoImageLoaderProvider implements IImageLoaderProvider {
    @Override
    public void loadImage(ImageRequest request) {
        Picasso.with(ServiceApplication.getInstance()).load(request.getUrl()).placeholder(request.getPlaceHolder()).into(request.getImageView());
    }

    @Override
    public void loadImage(Context context, ImageRequest request) {
        Picasso.with(context).load(request.getUrl()).placeholder(request.getPlaceHolder()).into(request.getImageView());
    }
}
