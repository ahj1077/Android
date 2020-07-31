package com.an.cinemaheaven.Api;

public class Api {
    public static String writer = "userID";
    public static String baseUrl = "http://boostcourse-appapi.connect.or.kr";
    public static String port = "10000";
    public static String movieListUrl = baseUrl + ':' + port +"/movie/readMovieList";
    public static String movieDetailUrl = baseUrl + ':' + port +"/movie/readMovie?id=";
    public static String movieCommentUrl = baseUrl + ':'+ port +"/movie/readCommentList?id=";
    public static String createCommentUrl = baseUrl + ':'+port+"/movie/createComment?";

}
