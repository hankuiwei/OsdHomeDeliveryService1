package com.lenovo.csd.eservice.http;
import android.widget.ImageView;

import com.lenovo.csd.eservice.http.provider.PicassoImageLoaderProvider;
import com.lenovo.csd.eservice.http.provider.base.IImageLoaderProvider;
import com.lenovo.csd.eservice.http.request.ImageRequest;

/**
 * 此处提供了毕加索方式，但是建议使用Glide方式（见Util类），因为在更高版本上，Glide性能更优
 * Created by shengtao
 * Date:2016/7/21
 * Time:11:03
 */
public class ImageLoader {
    private static volatile IImageLoaderProvider mProvider;
    public static IImageLoaderProvider getProvider() {
        if (mProvider == null) {
            synchronized (ImageLoader.class) {
                if (mProvider == null) {
                    mProvider = new PicassoImageLoaderProvider();
                }
            }
        }
        return mProvider;
    }
    public static void setImageFromInternet(ImageView iv, String url) {
        ImageRequest imageRequest = new ImageRequest.Builder().imgView(iv).url(url).create();
        ImageLoader.getProvider().loadImage(imageRequest);
    }
}
