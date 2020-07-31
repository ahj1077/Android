package com.an.cinemaheaven;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.an.cinemaheaven.Api.Api;
import com.an.cinemaheaven.data.MovieComment;
import com.an.cinemaheaven.data.MovieCommentList;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CommentViewActivity extends AppCompatActivity {

    TextView tv_title, tv_write, tv_rating;
    RatingBar ratingBar;
    CommentAdapter commentAdapter;
    ListView commentList;

    public String url = Api.movieCommentUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_view);

        Intent intent = getIntent();

        if(intent != null) {
            final int movie_id = intent.getExtras().getInt("id");
            final String title = intent.getExtras().getString("title").toString();
            final int grade = intent.getExtras().getInt("grade");
            String reviewer_rating = intent.getExtras().getString("reviewer_rating").toString();

            url += movie_id;
            commentAdapter = new CommentAdapter(getApplicationContext());
            commentList = (ListView) findViewById(R.id.lv_comment_list);

            ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();

            if(networkInfo != null) {
                StringRequest request = new StringRequest(
                        Request.Method.GET,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                processResponse(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }
                );

                request.setShouldCache(false);
                AppHelper.requestQueue.add(request);
            }
            else{
                String sql = "select * from MovieComment where movie_id = "+movie_id;
                Cursor c = MainActivity.database.rawQuery(sql,null);

                for(int i = 0; i<c.getCount(); i++){
                    c.moveToNext();

                    int id = c.getInt(0);
                    String writer = c.getString(2);
                    String time = c.getString(4);
                    float rating = c.getFloat(6);
                    String contents = c.getString(7);
                    int recommend = c.getInt(8);
                    CommentItem  item = new CommentItem(id,writer,time,rating,contents,recommend);
                    commentAdapter.addItem(item);
                }
            }
            commentList.setAdapter(commentAdapter);

            tv_title = (TextView) findViewById(R.id.tv_movieTitle);
            tv_title.setText(title);

            setGradeImage(grade);

            tv_rating = (TextView) findViewById(R.id.tv_total_rating);
            tv_rating.setText(reviewer_rating);

            tv_write = (TextView) findViewById(R.id.tv_write_comment);
            tv_write.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), WriteCommentActivity.class);
                    intent.putExtra("id",movie_id);
                    intent.putExtra("title", title);
                    intent.putExtra("grade", grade);
                    startActivity(intent);
                }
            });

            ratingBar = (RatingBar) findViewById(R.id.rbar_total_rating);
            ratingBar.setRating(Float.parseFloat(reviewer_rating));
        }
    }

    public void setGradeImage(int g){
        ImageView iv_grade = (ImageView) findViewById(R.id.iv_grade);
        int imageRes = 0;

        switch(g){
            case 12:
                imageRes = R.drawable.ic_12;
                break;
            case 15:
                imageRes = R.drawable.ic_15;
                break;
            case 19:
                imageRes = R.drawable.ic_19;
                break;
            case 0:
                imageRes = R.drawable.ic_all;
                break;
        }

        iv_grade.setImageResource(imageRes);
    }


    public void processResponse(String response){
        Gson gson = new Gson();
        MovieCommentList movieCommentList = gson.fromJson(response, MovieCommentList.class);

        if(movieCommentList.code == 200){
            MovieComment movieComment;

            for(int i = 0; i < movieCommentList.result.size(); i++){
                movieComment = movieCommentList.result.get(i);
                int id = movieComment.id;
                String writer = movieComment.writer;
                int movieId = movieComment.movieId;
                String writer_image = movieComment.writer_image;
                String time = movieComment.time;
                int timestamp = movieComment.timestamp;
                float rating = movieComment.rating;
                String contents = movieComment.contents;
                int recommend = movieComment.recommend;
                CommentItem  item = new CommentItem(id,writer,movieId,writer_image,time,timestamp,rating,contents,recommend);
                commentAdapter.addItem(item);
            }

            commentList.setAdapter(commentAdapter);
        }
    }
}
