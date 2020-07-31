package com.an.cinemaheaven.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class databaseHelper extends SQLiteOpenHelper {

    String databaseName = "Movie";

    public databaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //데이터 베이스를 처음 생성하는 경우
    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableName = "MovieList";

        String sql = "create table if not exists "+tableName+"("
                +"_id integer PRIMARY KEY autoincrement,"
                +"id integer, "
                +"title text, "
                +"date text,"
                +"user_rating float,"
                +"audience_rating float,"
                +"reviewer_rating float,"
                +"reservation_rate float,"
                +"reservation_grade integer,"
                +"grade integer,"
                +"image text)";

        String sql2 = "create table if not exists MovieInfo("
                +"_id integer PRIMARY KEY autoincrement, "
                +"id integer, "
                +"title text,"
                +"thumb text,"
                +"photos text,"
                +"videos text,"
                +"genre text,"
                +"duration integer,"
                +"audience integer,"
                +"synopsis text,"
                +"director text,"
                +"actor text,"
                +"like_cnt integer,"
                +"dislike_cnt integer)";

        String sql3 = "create table if not exists MovieComment("
                +"review_id integer PRIMARY KEY autoincrement,"
                +"movie_id integer,"
                +"writer text,"
                +"writer_image text,"
                +"time text,"
                +"timestamp text,"
                +"rating float,"
                +"contents text,"
                +"recommend integer)";

        try{
            db.execSQL(sql);
            db.execSQL(sql2);
            db.execSQL(sql3);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion > 1){
            db.execSQL("DROP TABLE IF EXISTS " + databaseName);
        }
    }

}
