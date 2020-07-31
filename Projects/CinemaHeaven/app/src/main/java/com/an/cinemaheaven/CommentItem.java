package com.an.cinemaheaven;

import android.os.Parcel;
import android.os.Parcelable;

/*
{
"id":6397,
"writer":"domMorello",
"movieId":1,
"writer_image":null,
"time":"2020-03-30 15:31:38",
"timestamp":1585549898,
"rating":4.0,
"contents":"ㅎㅎㅎ",
"recommend":1}

 */
public class CommentItem implements Parcelable {

    int id;
    String writer;
    int movieId;
    String writer_image;
    String time;
    int timestamp;
    float rating;
    String contents;
    int recommend;

    public CommentItem(int id, String writer, int movieId, String writer_image, String time, int timestamp, float rating, String contents, int recommend) {
        this.id = id;
        this.writer = writer;
        this.movieId = movieId;
        this.writer_image = writer_image;
        this.time = time;
        this.timestamp = timestamp;
        this.rating = rating;
        this.contents = contents;
        this.recommend = recommend;
    }

    public CommentItem(int id, String writer , String time, float rating, String contents, int recommend){
        this.id = id;
        this.writer = writer;
        this.time = time;
        this.rating = rating;
        this.contents = contents;
        this.recommend = recommend;
    }

    public CommentItem(Parcel in){
        this.id = in.readInt();
        this.writer = in.readString();
        this.movieId = in.readInt();
        this.writer_image = in.readString();
        this.time =in.readString();
        this.timestamp = in.readInt();
        this.rating =in.readFloat();
        this.contents =in.readString();
        this.recommend = in.readInt();

    }

    public static final Creator<CommentItem> CREATOR = new Creator<CommentItem>() {
        @Override
        public CommentItem createFromParcel(Parcel in) {
            return new CommentItem(in);
        }

        @Override
        public CommentItem[] newArray(int size) {
            return new CommentItem[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getWriter_image() {
        return writer_image;
    }

    public void setWriter_image(String writer_image) {
        this.writer_image = writer_image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getRecommend() {
        return recommend;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.writer);
        dest.writeInt(this.movieId);
        dest.writeString(this.writer_image);
        dest.writeString(this.time);
        dest.writeInt(this.timestamp);
        dest.writeFloat(this.rating);
        dest.writeString(this.contents);
        dest.writeInt(this.recommend);

    }
}
