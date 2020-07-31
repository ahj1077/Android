package com.an.cinemaheaven;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.an.cinemaheaven.data.Movie;
import com.bumptech.glide.Glide;

public class MoviePosterFragment extends Fragment {

    public int id;
    public String title;
    public String date;
    public float user_rating;
    public float audience_rating;
    public float reviewer_rating;
    public float reservation_rate;
    public int reservation_grade;
    public int grade;
    public String imageUrl;

    public MoviePosterFragment(Movie movie) {
        this.id = movie.id;
        this.title = movie.title;
        this.date = movie.date;
        this.user_rating = movie.user_rating;
        this.audience_rating = movie.audience_rating;
        this.reviewer_rating = movie.reviewer_rating;
        this.reservation_rate = movie.reservation_rate;
        this.reservation_grade = movie.reservation_grade;
        this.grade = movie.grade;
        this.imageUrl = movie.image;
    }

    public  interface showDetailCallback {
         void onDetailClicked(int id);
    }

    private showDetailCallback callback = null;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof showDetailCallback) {
            callback = (showDetailCallback) context;
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_movie_poster, container, false);
        ImageView iv_poster = rootView.findViewById(R.id.iv_poster);
        TextView tv_title = rootView.findViewById(R.id.tv_movieTitle_in_poster_fragment);
        TextView tv_reservationRate = rootView.findViewById(R.id.tv_reservation_rate);
        TextView tv_grade = rootView.findViewById(R.id.tv_grade);
        Button btn_showDetail = rootView.findViewById(R.id.btn_show_detail);

        btn_showDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               callback.onDetailClicked(id);
            }
        });

        Glide.with(this).load(imageUrl).into(iv_poster);

        tv_title.setText(title);
        tv_reservationRate.setText(Float.toString(reservation_rate) + "%");
        tv_grade.setText(Integer.toString(grade)+"세 관람가");

        return rootView;
    }

}
