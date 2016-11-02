package com.lenovo.csd.eservice.adapter;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.homedao.bean.Attachment;
import com.homedao.dao.AttachmentDao;
import com.homedao.dao.DaoSession;
import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.http.AttachmentUploadUtils;
import com.lenovo.csd.eservice.http.ErrorCode;
import com.lenovo.csd.eservice.http.NetInterface;
import com.lenovo.csd.eservice.util.DaoUtils;
import com.lenovo.csd.eservice.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 彤 on 2016/8/29.
 */
public class LocalAttachAdapter extends RecyclerView.Adapter<LocalAttachAdapter.ViewHolder> {
    private Activity context;
    private ArrayList<Attachment> attachments;
    private LayoutInflater mInflater;
    private String token;
    private UploadResult linstenner;
    private ArrayList<AsyncTask> tasks;


    public static int SUCCESS = 200;
    public static int FAIL = 300;

    public LocalAttachAdapter(Activity context, ArrayList<Attachment> attachments, String token) {
        this.context = context;
        this.attachments = attachments;
        this.token = token;
        tasks = new ArrayList<>();
        mInflater = LayoutInflater.from(this.context);
        if (context instanceof UploadResult) {
            linstenner = (UploadResult) context;
        } else {
            try {
                throw new Exception("the context must implements the UploadResult");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(mInflater.inflate(R.layout.item_localattachment_recyclerview, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Attachment attachment = attachments.get(position);
        Utils.displayCompressedBitmap(context, holder.mImgAttach, attachment.getFile_path());
        holder.mTxtOrderId.setText(attachment.getOrder_code());
        holder.mTxtAttachType.setText(attachment.getType());
        holder.mTxtAttachSize.setText(attachment.getFile_size());
        holder.mTxtUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.checkInternetStatus(context) == 0) {
                    Toast.makeText(context, R.string.text_internet_unavalible, Toast.LENGTH_SHORT).show();
                    return;
                }
                uploadTask(attachment, holder);
            }
        });

        holder.mTxtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPos = attachments.indexOf(attachment);
                AttachmentDao dao = DaoUtils.getDaoSession(context).getAttachmentDao();
                dao.deleteByKey(attachment.getId());
                dao.getDatabase().query(dao.getTablename(), null, null, null, null, null, null, null).requery();
                linstenner.onDelete(currentPos);
            }
        });


    }

    @Override
    public int getItemCount() {
        return attachments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImgAttach;
        public TextView mTxtOrderId;
        public TextView mTxtAttachType;
        public TextView mTxtAttachSize;
        public TextView mTxtUpload;
        public TextView mTxtDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            mImgAttach = (ImageView) itemView.findViewById(R.id.img_attachItem);
            mTxtOrderId = (TextView) itemView.findViewById(R.id.text_orderId);
            mTxtAttachType = (TextView) itemView.findViewById(R.id.text_attachType);
            mTxtAttachSize = (TextView) itemView.findViewById(R.id.text_attachSize);
            mTxtUpload = (TextView) itemView.findViewById(R.id.text_uploadAttach);
            mTxtDelete = (TextView) itemView.findViewById(R.id.text_deleteAttach);
        }
    }

    /**
     * 对上传附件返回结果进行解析并执行相关逻辑
     */
    public int parseResult(String result, Long id) {
        //解析字符为空，返回失败
        if (TextUtils.isEmpty(result))
            return FAIL;
        try {
            JSONObject jsonObject = new JSONObject(result);
            int resultCode = jsonObject.getInt("status_code");
            if (resultCode == ErrorCode.STATUS_SUCCESS) {
                DaoSession session = DaoUtils.getDaoSession(context);
                AttachmentDao dao = session.getAttachmentDao();
                dao.deleteByKey(id);
                dao.getDatabase().query(dao.getTablename(), null, null, null, null, null, null).requery();
                return SUCCESS;
            } else {
                return FAIL;
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return FAIL;
    }

    //上传结果回调
    public interface UploadResult {
        void onSuccess(int position);

        void onFail(String attachID);

        void onDelete(int position);
    }

    /**
     * 刷新数据
     */
    public void refreshData(ArrayList<Attachment> datas) {
        this.attachments.clear();
        if (datas != null && datas.size() > 0) {
            this.attachments.addAll(datas);
            notifyDataSetChanged();
        }
    }

    public void cancelAllTask() {
//        for (AsyncTask task : tasks) {
//            if (task.getStatus() == AsyncTask.Status.RUNNING) {
//                task.cancel(true);
//                tasks.remove(task);
//            } else if (task.getStatus() == AsyncTask.Status.FINISHED) {
//                tasks.remove(task);
//            }
//        }
    }

    public void uploadTask(final Attachment attachment, final ViewHolder holder) {
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("order_code", attachment.getOrder_code());
        param.put("user_code", attachment.getUser_code());
        param.put("type", attachment.getType_id());
        final AttachmentUploadUtils.UploadAttachAsyncTask task = AttachmentUploadUtils.newTask(context, param, NetInterface.UPLOAD_ATTACHMENT_URL + "token=" + token, new AttachmentUploadUtils.UploadLinstenner() {
            @Override
            public void attachUploadStart() {
                holder.mTxtUpload.setEnabled(false);
                holder.mTxtUpload.setText("上传中...");
            }

            @Override
            public void attachUpResult(String result) {
                int currentpos = attachments.indexOf(attachment);
                holder.mTxtUpload.setEnabled(true);
                holder.mTxtUpload.setText("上传");
                if (parseResult(result, attachment.getId()) == SUCCESS) {
                    linstenner.onSuccess(currentpos);
                } else {
                    holder.mTxtUpload.setText("失败");
                    linstenner.onFail(attachment.getOrder_code());
                }
            }
        }, false);
        task.execute(attachment.getFile_path());
    }

}
