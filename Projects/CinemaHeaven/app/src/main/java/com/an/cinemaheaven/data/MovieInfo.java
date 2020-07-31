package com.an.cinemaheaven.data;

import java.io.Serializable;
import java.util.ArrayList;

public class MovieInfo implements Serializable {
    public String title;
    public int id;
    public String date;
    public float user_rating;
    public float audience_rating;
    public float reviewer_rating;
    public float reservation_rate;
    public int reservation_grade;
    public int grade;
    public String thumb;
    public String image;
    public String photos;
    public String videos;
    public String outlinks;
    public String genre;
    public int duration;
    public int audience;
    public String synopsis;
    public String director;
    public String actor;
    public int like;
    public int dislike;
}
