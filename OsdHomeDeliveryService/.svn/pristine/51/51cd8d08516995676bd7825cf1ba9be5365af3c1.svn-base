package com.lenovo.csd.eservice.services;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.lenovo.csd.eservice.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AppUpdateService extends Service {
    public final int DOWNLOADING = 0;
    public final int DOWNLOAD_FINISH = 1;
    public final int SHOW_NOTIFICATION = 3;
    private static final int NOTIFICTION_ID = 10;
    public static final String FILE_PATH = "file_path";
    public static final String DOWN_URL = "down_url";
    private NotificationCompat.Builder builder;
    private NotificationManager manager;
    public static boolean isDownloading = false;
    private DownloadAppThread downloadAppThread;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SHOW_NOTIFICATION:
//                    showNotification(msg.arg1);
                    showNotification(getResources().getString(R.string.text_downloading), true);
                    break;
                case DOWNLOADING://正在下载更新NOTIFICATION
                    updateNotification(msg.arg1);
                    break;
                case DOWNLOAD_FINISH://下载完成。安装apk
                    manager.cancel(NOTIFICTION_ID);
                    isDownloading = false;
                    String filePath = msg.getData().getString(FILE_PATH);
                    installApk(filePath);
                    stopSelf();//停止服务进程
                    break;
            }
        }
    };


    public AppUpdateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        if (isDownloading)
            return;
        String apk_url = intent.getStringExtra(DOWN_URL);
        if (apk_url != null && !TextUtils.isEmpty(apk_url)) {
            downloadAppThread = new DownloadAppThread(apk_url);
            downloadAppThread.start();
        }
    }

    public class DownloadAppThread extends Thread {
        private String dlUrl;

        public DownloadAppThread(String dlUrl) {
            this.dlUrl = dlUrl;
        }

        @Override
        public void run() {
            super.run();
            try {
                //设置存储文件的路径和文件名称
                Looper.prepare();
                handler.sendEmptyMessage(SHOW_NOTIFICATION);
                isDownloading = true;
                String savePath = null;
                if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                    savePath = Environment.getExternalStorageDirectory().getPath() + "/download";
                } else {
                    savePath = getExternalCacheDir().getPath() + "/download";
                }
                if (savePath != null) {//先创建文件夹
                    File saveFile = new File(savePath);
                    if (!saveFile.exists()) {
                        saveFile.mkdirs();
                    }
                    File apkFile = new File(saveFile, "homedelivery.apk");
//                    if (apkFile.exists())//文件存在，先删除
//                        apkFile.delete();
                    apkFile.createNewFile();
                    //获取网络字节流
                    URL url = new URL(dlUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.setConnectTimeout(10 * 1000);
                    connection.connect();
                    long total = connection.getContentLength();//待下载总量
                    long current = 0;//当前下载量
                    int percentage = 0;//百分比
                    int lastPercentage = 0;//上一次百分比
                    byte[] buffer = new byte[512];//缓冲区
                    InputStream inputStream = connection.getInputStream();
                    FileOutputStream fos = new FileOutputStream(apkFile);
                    int count = 0;
                    //弹出NOTIFICATION
                    while ((count = inputStream.read(buffer)) != -1) {
                        current += count;
                        percentage = (int) ((float) current / total * 100);
                        fos.write(buffer, 0, count);
                        if (percentage - lastPercentage >= 2 || percentage == 100) {
                            Message msg = handler.obtainMessage();
                            msg.what = DOWNLOADING;
                            msg.arg1 = percentage;
                            handler.sendMessage(msg);
                            lastPercentage = percentage;
                        }
                    }
                    //关闭管道流
                    fos.flush();
                    inputStream.close();
                    fos.close();
                    connection.disconnect();
                    //下载完成，发送消息安装apk
                    Message installMsg = handler.obtainMessage();
                    installMsg.what = DOWNLOAD_FINISH;
                    Bundle bundle = new Bundle();
                    bundle.putString(FILE_PATH, apkFile.getPath());
                    installMsg.setData(bundle);
                    handler.sendMessage(installMsg);
                }
            } catch (Exception e) {
                showNotification(getResources().getString(R.string.text_download_error), false);
                e.printStackTrace();
                stopSelf();
            } finally {
                isDownloading = false;
            }
        }
    }

    /**
     * 更新/弹出NOTIFICATION
     */
    private void showNotification(String contentText, boolean showTicker) {
        builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.icon_applogo)
                .setContentTitle(contentText);
        if (showTicker) {
            builder.setTicker(getResources().getString(R.string.text_start_download));
        }
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        builder.setAutoCancel(false);
        manager.notify(NOTIFICTION_ID, builder.build());
    }

    private void updateNotification(int progress) {
        builder.setProgress(100, progress, false);
        manager.notify(NOTIFICTION_ID, builder.build());
    }

    public void installApk(String filePath) {
        File apkFile = new File(filePath);
        if (!apkFile.exists()) {
            return;
        }
        // 通过Intent安装APK文件
        Intent i = new Intent();
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setAction(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        startActivity(i);
    }

}
