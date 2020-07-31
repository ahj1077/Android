package com.an.cinemaheaven;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Rating;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.an.cinemaheaven.Api.Api;
import com.an.cinemaheaven.data.MovieComment;
import com.an.cinemaheaven.data.MovieCommentList;
import com.an.cinemaheaven.data.MovieDetail;
import com.an.cinemaheaven.data.MovieInfo;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;

import org.w3c.dom.Comment;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MovieDetailFragment extends Fragment {

    ViewGroup rootView;
    private int likeCnt = 0;
    private int dislikeCnt = 0;
    private boolean isLike = false, isDislike = false;

    TextView tv_like, tv_dislike;
    TextView tv_viewAll, tv_write;
    TextView tv_title, tv_audience, tv_actor, tv_director;
    TextView tv_reservationGrade, tv_reservationRate, tv_releaseDate;
    TextView tv_duration, tv_genre, tv_synopsis, tv_rating;

    ImageView iv_like, iv_dislike;
    ImageView iv_poster, iv_grade;

    RatingBar ratingBar;
    Button btn_reservation;

    ListView listView;
    RecyclerView recyclerView;
    CommentAdapter commentAdapter;
    galleryAdapter galleryAdapter;

    String title;
    int id;
    int imageRes;
    float reviewer_rating, reservation_rate;
    int grade, reservation_grade;
    String date;

    String thumb;
    String genre;
    int audience;
    String synopsis;
    String director;
    String actor ;
    int like;
    int dislike;
    int duration;
    String photos;
    String videos;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_movie_detail, container, false);

        Bundle bundle = getArguments();
        ConnectivityManager manager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        listView = (ListView) rootView.findViewById(R.id.lv_comment);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_gallery);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        commentAdapter = new CommentAdapter(getContext());
        galleryAdapter = new galleryAdapter(getContext());

        if(networkInfo != null) {
            MovieInfo movieInfo = (MovieInfo) bundle.getSerializable("movieInfo");

            title = movieInfo.title;
            id = movieInfo.id;
            date = movieInfo.date;
            float user_rating = movieInfo.user_rating;
            float audience_rating = movieInfo.audience_rating;
            reviewer_rating = movieInfo.user_rating;
            reservation_rate = movieInfo.reservation_rate;
            reservation_grade = movieInfo.reservation_grade;
            grade = movieInfo.grade;
            thumb = movieInfo.thumb;
            genre = movieInfo.genre;
            audience = movieInfo.audience;
            synopsis = movieInfo.synopsis;
            director = movieInfo.director;
            actor = movieInfo.actor;
            like = movieInfo.like;
            dislike = movieInfo.dislike;
            duration = movieInfo.duration;
            photos = movieInfo.photos;
            videos = movieInfo.videos;

            String updateMovieListSql = "update MovieList set " +
                    "title = '" + title
                    + "',date = '" + date
                    + "',reviewer_rating = " + reviewer_rating
                    + ",reservation_rate = " + reservation_rate
                    + ",reservation_grade = " + reservation_grade
                    + ",grade = " + grade
                    + " where id = "+ id;

            String updateMovieInfoSql = "update MovieInfo set " +
                    "title = '" + title
                    + "',genre = '" + genre
                    + "',audience = " + audience
                    + ",synopsis = \"" + synopsis
                    + "\",director = '" + director
                    + "',actor = '" + actor
                    + "',like_cnt = " + like
                    + ",dislike_cnt = " + dislike
                    + ",duration = " + duration + " where id = "+ id;

            MainActivity.database.execSQL(updateMovieListSql);
            MainActivity.database.execSQL(updateMovieInfoSql);

            readCommentList(id, -1);
        }
        else{
            id = bundle.getInt("id");

            String sql = "select * from MovieList where id = " + id;
            Cursor c1 = MainActivity.database.rawQuery(sql,null);
            c1.moveToNext();

            String sql2 = "select * from MovieInfo where id = " + id;
            Cursor c2 = MainActivity.database.rawQuery(sql2,null);
            c2.moveToNext();

            title = c1.getString(2);
            date = c1.getString(3);
            reviewer_rating = c1.getFloat(6);
            reservation_rate = c1.getFloat(7);
            reservation_grade = c1.getInt(8);
            grade = c1.getInt(9);

            thumb = c2.getString(3);
            photos = c2.getString(4);
            videos = c2.getString(5);
            genre = c2.getString(6);
            duration = c2.getInt(7);
            audience = c2.getInt(8);
            synopsis = c2.getString(9);
            director = c2.getString(10);
            actor = c2.getString(11);
            like = c2.getInt(12);
            dislike = c2.getInt(13);

            String sql3 = "select * from MovieComment where movie_id = "+id;
            Cursor c3 = MainActivity.database.rawQuery(sql3,null);

            for(int i = 0; i<c3.getCount(); i++){
                c3.moveToNext();

                int id = c3.getInt(0);
                String writer = c3.getString(2);
                String time = c3.getString(4);
                float rating = c3.getFloat(6);
                String contents = c3.getString(7);
                int recommend = c3.getInt(8);
                CommentItem  item = new CommentItem(id, writer,time,rating,contents,recommend);
                commentAdapter.addItem(item);
            }

            listView.setAdapter(commentAdapter);
            recyclerView.setAdapter(galleryAdapter);
        }


        iv_like = (ImageView) rootView.findViewById(R.id.rb_like);
        iv_dislike = (ImageView) rootView.findViewById(R.id.rb_dislike);
        iv_poster = (ImageView) rootView.findViewById(R.id.iv_movie);
        iv_grade = (ImageView) rootView.findViewById(R.id.iv_grade);

        tv_title = (TextView) rootView.findViewById(R.id.tv_title);
        tv_reservationRate = (TextView) rootView.findViewById(R.id.tv_reservation_rate);
        tv_releaseDate = (TextView) rootView.findViewById(R.id.tv_release_date);
        tv_like = (TextView) rootView.findViewById(R.id.tv_like);
        tv_dislike = (TextView) rootView.findViewById(R.id.tv_dislike);
        tv_viewAll = (TextView) rootView.findViewById(R.id.tv_viewAll);
        tv_write = (TextView) rootView.findViewById(R.id.tv_write);
        tv_reservationGrade = (TextView) rootView.findViewById(R.id.tv_reservation_grade);
        tv_genre = (TextView) rootView.findViewById(R.id.tv_genre);
        tv_duration = (TextView) rootView.findViewById(R.id.tv_duration);
        tv_synopsis =(TextView) rootView.findViewById(R.id.tv_synopsis);
        tv_actor = (TextView) rootView.findViewById(R.id.tv_actor);
        tv_director = (TextView) rootView.findViewById(R.id.tv_director);
        tv_audience = (TextView) rootView.findViewById(R.id.tv_audience);
        tv_rating = (TextView) rootView.findViewById(R.id.tv_numeric_Rating);
        ratingBar = (RatingBar) rootView.findViewById(R.id.rbar_rating);
        btn_reservation = (Button) rootView.findViewById(R.id.btn_reservation);

        btn_reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "예매 하기 버튼이 눌렸습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        tv_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWriteCommentActivity();
            }
        });
        tv_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCommentViewActivity();
            }
        });
        iv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //아무것도 눌리지 않은 상태
                if(!isLike && !isDislike){
                    incLike();
                }
                //좋아요만 눌린 상태
                else if(isLike && !isDislike){
                    decLike();
                }
                //싫어요만 눌린 상태
                else if(!isLike && isDislike){
                    decDislike();
                    incLike();
                }
                //둘 다 눌려있을 수는 없음
            }
        });

        iv_dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //아무것도 눌리지 않은 상태
                if(!isLike && !isDislike){
                    incDislike();
                }
                //좋아요만 눌린 상태
                else if(isLike && !isDislike){
                    decLike();
                    incDislike();
                }
                //싫어요만 눌린 상태
                else if(!isLike && isDislike){
                    decDislike();
                }
                //둘 다 눌려있을 수는 없음
            }
        });


        likeCnt = like;
        dislikeCnt = dislike;

        tv_title.setText(title);
        tv_genre.setText(genre);
        tv_director.setText(director);
        tv_actor.setText(actor);
        ratingBar.setRating(reviewer_rating);
        tv_rating.setText(Float.toString(reviewer_rating));
        tv_releaseDate.setText(date + " 개봉");
        tv_duration.setText(Integer.toString(duration)+"분");
        tv_like.setText(Integer.toString(like));
        tv_dislike.setText(Integer.toString(dislike));
        tv_synopsis.setText(synopsis);
        tv_audience.setText(Integer.toString(audience) + "명");
        tv_reservationGrade.setText(Integer.toString(reservation_grade) + "위");
        tv_reservationRate.setText(Float.toString(reservation_rate) + "%");

        if(networkInfo != null) {
            Glide.with(this).load(thumb).into(iv_poster);
            getGallery();
            recyclerView.setAdapter(galleryAdapter);
        }
        setGradeImage(grade);

        insertMovieInfoIntoDB(id, title);

        return rootView;
    }

    public void getGallery(){

        if(photos != null) {
            String[] photo = photos.split(",");
            for (int i = 0; i < photo.length; i++) {
                final int idx = i;
                Glide.with(this)
                        .asBitmap()
                        .load(photo[i])
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                galleryAdapter.addItem(new galleryItem(resource, false));
                                galleryAdapter.notifyItemRangeInserted(idx, 1);
                            }
                        });
            }
        }

        if(videos != null) {
            String[] video = videos.split(",");

            for (int i = 0; i < video.length; i++) {
                String id = video[i].substring(video[i].lastIndexOf("/") + 1);
                String url = "http://img.youtube.com/vi/" + id + "/default.jpg";

                Glide.with(this).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        galleryAdapter.addItem(new galleryItem(resource, true));
                        galleryAdapter.notifyItemRangeInserted(galleryAdapter.getItemCount() - 1, 1);
                    }
                });
            }
        }
        recyclerView.setAdapter(galleryAdapter);
    }

    public void insertMovieInfoIntoDB(int _id, String _title){
        if(MainActivity.helper != null){
            final int f_id = _id;
            final String f_title = _title;

            Glide.with(this).asBitmap().load(thumb).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    MainActivity.saveFile(getContext(), resource, f_id, f_title, false);
                }
            });

            ContentValues values = new ContentValues();
            values.put("id", id);
            values.put("title", title);
            values.put("photos", photos);
            values.put("videos", videos);
            values.put("genre", genre);
            values.put("duration", duration);
            values.put("audience", audience);
            values.put("synopsis", synopsis);
            values.put("director", director);
            values.put("actor", actor);
            values.put("like_cnt", like);
            values.put("dislike_cnt", dislike);
            MainActivity.database.insert("MovieInfo", null, values);
        }

    }


    public void setGradeImage(int g){
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

    public void readCommentList(int id,int limit){

        String url = Api.movieCommentUrl + id;
        if(limit != -1)
            url += ("&limit=" + limit);

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        processCommentResponse(response);
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

    public void processCommentResponse(String response){
        Gson gson =  new Gson();
        MovieCommentList movieCommentList = gson.fromJson(response,MovieCommentList.class);

        if(movieCommentList.code == 200) {
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

                String sql = "insert or replace into MovieComment(review_id, movie_id, writer, time, timestamp, rating, contents, recommend) "
                        +"values ("+id +","+movieId+",\'"+writer+"\',\'"+time+"\',"+timestamp+","+rating+",\'"+contents+"\',"+recommend+")";
                MainActivity.database.execSQL(sql);
            }

            listView.setAdapter(commentAdapter);
        }
    }

    public void incLike(){
        isLike = true;
        likeCnt++;
        iv_like.setSelected(true);
        tv_like.setText(Integer.toString(likeCnt));

    }

    public void decLike(){
        isLike = false;
        likeCnt--;
        iv_like.setSelected(false);
        tv_like.setText(Integer.toString(likeCnt));
    }

    public void incDislike(){
        isDislike = true;
        dislikeCnt++;
        iv_dislike.setSelected(true);
        tv_dislike.setText(Integer.toString(dislikeCnt));
    }

    public void decDislike(){
        isDislike = false;
        dislikeCnt--;
        iv_dislike.setSelected(false);
        tv_dislike.setText(Integer.toString(dislikeCnt));
    }

    //한줄 평 모두보기 액티비티를 보여주는 메소드
    public void showCommentViewActivity(){
        Intent intent = new Intent(getContext(), CommentViewActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("title", title);
        intent.putExtra("grade", grade);
        intent.putExtra("reviewer_rating",Float.toString(reviewer_rating));
        startActivity(intent);
    }

    //한줄 평 작성 액티비티를 보여주는 메소드
    public void showWriteCommentActivity(){
        Intent intent = new Intent(getContext(), WriteCommentActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("title", title);
        intent.putExtra("grade", grade);
        startActivity(intent);
    }
}
