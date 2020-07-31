package com.an.cinemaheaven.data;

public class Movie {
    public int id;
    public String title;
    public String date;
    public float user_rating;
    public float audience_rating;
    public float reviewer_rating;
    public float reservation_rate;
    public int reservation_grade;
    public int grade;
    public String image;

    public Movie(int id, String title, String date, float user_rating, float audience_rating, float reviewer_rating, float reservation_rate, int reservation_grade, int grade, String image) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.user_rating = user_rating;
        this.audience_rating = audience_rating;
        this.reviewer_rating = reviewer_rating;
        this.reservation_rate = reservation_rate;
        this.reservation_grade = reservation_grade;
        this.grade = grade;
        this.image = image;
    }
}
