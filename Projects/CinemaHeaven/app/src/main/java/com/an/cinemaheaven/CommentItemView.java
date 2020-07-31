package com.an.cinemaheaven;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.an.cinemaheaven.R;

public class CommentItemView extends RelativeLayout {

    TextView tv_userId;
    TextView tv_recommendcnt;
    TextView tv_contents;
    RatingBar userRatingbar;

    public CommentItemView(Context context) {
        super(context);
        init(context);
    }

    public CommentItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.comment_item, this, true);

        tv_userId = (TextView) findViewById(R.id.tv_userId);
        tv_recommendcnt = (TextView) findViewById(R.id.tv_recommendCnt);
        tv_contents = (TextView) findViewById(R.id.tv_contents);
        userRatingbar = (RatingBar) findViewById(R.id.rbar_userRating);
    }

    public void setTv_userId(String userId){
        tv_userId.setText(userId);
    }

    public void setUserRatingbar(float rate){
        userRatingbar.setRating(rate);
    }

    public void setTv_commentText(String comment){
        tv_contents.setText(comment);
    }

    public void setTv_recommendCnt(String cnt){
        tv_recommendcnt.setText(cnt);
    }

}
