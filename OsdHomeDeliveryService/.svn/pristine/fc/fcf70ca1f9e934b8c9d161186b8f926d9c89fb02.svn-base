package com.lenovo.csd.eservice.util;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;

/**
 * Created by 彤 on 2016/8/23.
 */
public class FileUtils {
    public static final String PIC_File = "HomeDelivery";
    public static final String ATTACH_FILE = "AttachThumb";

    public static File getPicFile(Context context) {
        File root = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            root = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), PIC_File);
            if (!root.exists())
                root.mkdirs();
            return root;
        } else {

            root = context.getFilesDir();
            return root;
//            Toast.makeText(context, "SD卡未装载", Toast.LENGTH_SHORT).show();
        }
    }

    //附件文件存储地址
    public static File getAttachFile(Context context) {
        File root = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            root = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), ATTACH_FILE);
            if (!root.exists())
                root.mkdirs();
            return root;
        } else {
            root = context.getFilesDir();
            Toast.makeText(context, "SD卡未装载", Toast.LENGTH_SHORT).show();
            return root;
        }
    }

    public static File getCache(Context context) {
        return context.getCacheDir();
    }
}
