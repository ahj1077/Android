package com.an.cinemaheaven;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class CommentAdapter extends BaseAdapter {
    ArrayList<CommentItem> arrayList = new ArrayList<CommentItem>();
    Context context;

    public CommentAdapter(Context context) {
        this.context = context;
    }

    public void addItem(CommentItem item){
        arrayList.add(item);
    }

    public ArrayList<CommentItem> getArrayList() {
        return arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public CommentItem getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CommentItemView view = null;

            if (convertView == null) {
                view = new CommentItemView(context.getApplicationContext());
            }
            else {
                view = (CommentItemView) convertView;
            }

            CommentItem item = arrayList.get(position);
            view.setTv_userId(item.getWriter());
        view.setTv_commentText(item.getContents());
        view.setUserRatingbar(item.getRating());
        view.setTv_recommendCnt(Integer.toString(item.getRecommend()));

        return view;
    }
}
