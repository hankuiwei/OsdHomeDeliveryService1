package com.lenovo.csd.eservice.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.entity.base.MyCommentList;
import com.lenovo.csd.eservice.util.Utils;
import com.lenovo.csd.eservice.widget.CircleImageView;

import java.util.List;

/**
 * Created by hankw on 16-7-21.
 */
public class CommentListViewAdapter extends BaseAdapter {

    private final String TAG = getClass().getSimpleName();

    private Context mcontext;
    private List<MyCommentList.Data> mlist;

    public CommentListViewAdapter(Context mcontext, List<MyCommentList.Data> list) {
        this.mcontext = mcontext;
        this.mlist = list;
    }
    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.item_comment,parent,false);
            int[] ids = {R.id.comment_head,R.id.comment_stars,R.id.comment_title,R.id.Comment,R.id.comment_line};
            viewHolder = new ViewHolder(convertView,ids);
            View[] views = viewHolder.getItemView();
            MyCommentList.Data myComment= (MyCommentList.Data) getItem(position);
            String  headUrl = null;
            int stars = 0;
            String[] comment_titles = null;
            String comment = null;
            if (myComment != null) {
               headUrl = myComment.getUserimg();
               stars = myComment.getScore();
               comment_titles = myComment.getLables();
               comment = myComment.getContent();
            }
            CircleImageView circularImage = (CircleImageView) views[0];
            Utils.loadImageWithGlide(mcontext,headUrl,circularImage,R.drawable.default_load_img);
            RatingBar mRatingBar = (RatingBar) views[1];
            if (stars != 0){
                mRatingBar.setNumStars(stars);
            }

            LinearLayout mtitle_lay = (LinearLayout) views[2];
            if (mtitle_lay != null) {
                mtitle_lay.removeAllViews();
            }
            if (comment_titles != null)
            for (int i = 0 ; i < comment_titles.length; i ++) {
                LinearLayout linearLayout = new LinearLayout(mcontext);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                linearLayout.setBackgroundResource(R.drawable.item_comment_textview);
                TextView textView = new TextView(mcontext);
                textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                textView.setTextSize(8f);
                textView.setTextColor(Color.parseColor("#FFFFFF"));
                LinearLayout.LayoutParams  layoutParamsT = (LinearLayout.LayoutParams) textView.getLayoutParams();
                layoutParamsT.setMargins(mcontext.getResources().getDimensionPixelSize(R.dimen.layout_13dp),mcontext.getResources().getDimensionPixelSize(R.dimen.layout_5dp),mcontext.getResources().getDimensionPixelSize(R.dimen.layout_13dp),mcontext.getResources().getDimensionPixelSize(R.dimen.layout_5dp));
                textView.setLayoutParams(layoutParamsT);
                //textView.

                    LinearLayout.LayoutParams  layoutParams = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
                    layoutParams.setMargins(0,0,mcontext.getResources().getDimensionPixelSize(R.dimen.layout_5dp),0);
                    linearLayout.setLayoutParams(layoutParams);

                textView.setText(comment_titles[i]);
                linearLayout.addView(textView);
                mtitle_lay.addView(linearLayout);
            }
            TextView mcomment_text = (TextView) views[3];
            mcomment_text.setText(comment);
            LinearLayout line = (LinearLayout) views[4];
            /*if (position == (getCount()-1)){
                line.setVisibility(View.GONE);
            }*/

        }
        return convertView;
    }

    public class ViewHolder {
        private View[] itemView;

        private ViewHolder(View v, int[] ids) {
            itemView = new View[ids.length];
            for (int i = 0; i < ids.length; i++ ) {
                itemView [i] = v.findViewById(ids [i] );
            }
            v.setTag(this);
        }

        public View[] getItemView() {
            return itemView;
        }

    }
}
