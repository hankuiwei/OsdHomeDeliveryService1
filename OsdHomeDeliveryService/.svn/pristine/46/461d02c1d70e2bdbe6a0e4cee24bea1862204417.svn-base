package com.lenovo.csd.eservice.http;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Looper;
import android.widget.Toast;


import com.lenovo.csd.eservice.util.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by 彤 on 2016/8/23.
 */
public class AttachmentUploadUtils {
    public static final String CACHE_FILE = "/temp.jpg";
    public static final String BOUNDARY = "--startjdbwljkkklxbfdybksfhdbqend";
//    public static final int UPLOAD_SUCCESS = 0;
//    public static final int UPLOAD_FAILED = 1;



    public static String generateCompressedPic(Context context, String filePath, String destinationPath) {
        File sourceFile = new File(filePath);
        if (sourceFile.exists()) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filePath, options);
            options.inJustDecodeBounds = false;
            int realWidth = options.outWidth;
            int realHeight = options.outHeight;
            int targetWidth = 320;
            int targetHeight = 480;
            options.inSampleSize = 1;
            if (realWidth > realHeight && realWidth > targetWidth) {
                options.inSampleSize = (int) ((float) realWidth / targetWidth);
            } else if (realWidth < realHeight && realHeight > targetHeight) {
                options.inSampleSize = (int) ((float) realHeight / targetHeight);
            }
            if (options.inSampleSize < 1) {
                options.inSampleSize = 1;
            }

            Bitmap sourceBitmap = BitmapFactory.decodeFile(filePath, options);
            int qOptions = 100;
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            sourceBitmap.compress(Bitmap.CompressFormat.PNG, qOptions, bao);
            while (bao.toByteArray().length / 1024 > 1024) {
                qOptions -= 10;
                bao.reset();
                sourceBitmap.compress(Bitmap.CompressFormat.PNG, qOptions, bao);
                if (qOptions == 0)
                    break;
            }
//            String tempFilePath = FileUtils.getPicFile(context).getPath() + CACHE_FILE;
            File targetFile = new File(destinationPath);
            if (targetFile.exists())
                targetFile.delete();
            try {
                FileOutputStream fos = new FileOutputStream(destinationPath);
                fos.write(bao.toByteArray());
                fos.flush();
                fos.close();
                bao.close();
                return destinationPath;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            Toast.makeText(context, "文件不存在", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    /**
     * 创建一个线程上传文件
     *
     * @return
     */
    public static UploadAttachAsyncTask newTask(Context context, HashMap<String, String> params, String url, UploadLinstenner linstenner, boolean needCompress) {
        UploadAttachAsyncTask task = new UploadAttachAsyncTask(context, params, url, linstenner, needCompress);
        return task;
    }

    public static class UploadAttachAsyncTask extends AsyncTask<String, Integer, String> {
        private Context context;
        private UploadLinstenner linstenner;
        private HashMap<String, String> params;
        private String url;//文件上传地址
        private boolean needCompress;

        //        private UploadCallback callback;
        public UploadAttachAsyncTask(Context context, HashMap<String, String> params, String url, UploadLinstenner linstenner, boolean needCompress) {
            this.context = context;
            this.params = params;
            this.linstenner = linstenner;
            this.url = url;
            this.needCompress = needCompress;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            linstenner.attachUploadStart();
        }

        @Override
        protected String doInBackground(String... params) {
            if (Looper.myLooper() == null)
                Looper.prepare();
            String tempFilePath = null;
            if (needCompress) {
                tempFilePath = generateCompressedPic(context, params[0], FileUtils.getPicFile(context).getPath() + CACHE_FILE);
            } else {
                tempFilePath = params[0];
            }
            if (tempFilePath == null) {
                return null;
            }
            File file = new File(tempFilePath);
            if (!file.exists())
                return null;
            return uploadFile(file, this.params, url);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            linstenner.attachUpResult(result);
        }
    }

    /**
     * 实现上传的功能代码
     *
     * @param file
     */
    public static String uploadFile(File file, HashMap<String, String> params, String url) {
        StringBuilder sb = new StringBuilder();
        //添加正常的表单参数
        if (params != null) {
            for (String key : params.keySet()) {
                sb.append("--" + BOUNDARY + "\r\n");
                sb.append("Content-Disposition:form-data; name=\"" + key + "\"" + "\r\n");
                sb.append("\r\n");
                sb.append(params.get(key) + "\r\n");
            }
        }
        //添加头文件
        sb.append("--" + BOUNDARY + "\r\n");
        sb.append("Content-Disposition:form-data;name=\"file\";filename=\"" + file.getName() + "\"" + "\r\n");
        sb.append("Content-Type:image/*" + "\r\n");
        sb.append("\r\n");

        try {
            byte[] head = sb.toString().getBytes("UTF-8");
            byte[] end = ("\r\n--" + BOUNDARY + "\r\n").getBytes("UTF-8");

            URL uploadUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) uploadUrl.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + BOUNDARY);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");

            OutputStream os = conn.getOutputStream();
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int len;
            //先写入请求头
            os.write(head);

            while ((len = fis.read(buffer)) != -1) {
                os.write(buffer);
            }
            //写入结束符
            os.write(end);
            os.flush();
            fis.close();

            //接收响应
            InputStream is = conn.getInputStream();
            int ch;
            StringBuffer sbr = new StringBuffer();
            while ((ch = is.read()) != -1) {
                sbr.append((char) ch);
            }
            String result = sbr.toString();
            is.close();
            os.close();
            conn.disconnect();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public interface UploadLinstenner {
        void attachUploadStart();

        void attachUpResult(String result);
    }
}
