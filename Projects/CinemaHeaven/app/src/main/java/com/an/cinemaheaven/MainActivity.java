package com.an.cinemaheaven;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.an.cinemaheaven.Api.Api;
import com.an.cinemaheaven.data.Movie;
import com.an.cinemaheaven.data.MovieDetail;
import com.an.cinemaheaven.data.MovieInfo;
import com.an.cinemaheaven.data.MovieList;
import com.an.cinemaheaven.db.databaseHelper;
import com.an.cinemaheaven.ui.home.HomeFragment;
import com.an.cinemaheaven.ui.movieApi.MovieApiFragment;
import com.an.cinemaheaven.ui.reservation.ReservationFragment;
import com.an.cinemaheaven.ui.userSetting.userSettingFragment;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;

import com.bumptech.glide.request.transition.Transition;


import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MoviePosterFragment.showDetailCallback{

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    public static databaseHelper helper;
    public static SQLiteDatabase database;
    private ConnectivityManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(manager == null)
            manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if(AppHelper.requestQueue  == null)
            AppHelper.requestQueue = Volley.newRequestQueue(getApplicationContext());

        if(helper == null) {
            helper = new databaseHelper(this, "Movie", null, 1);
            database = helper.getReadableDatabase();
            database = helper.getWritableDatabase();
        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_movie_api, R.id.nav_reservation,
                R.id.nav_user_setting)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.nav_home) {
                    toolbar.setTitle(R.string.menu_home);
                    loadFragment(new HomeFragment());
                } else if(id == R.id.nav_movie_api) {
                    toolbar.setTitle(R.string.menu_api);
                    loadFragment(new MovieApiFragment());
                } else if(id == R.id.nav_reservation) {
                    toolbar.setTitle(R.string.menu_reservation);
                    loadFragment(new ReservationFragment());
                } else if(id == R.id.nav_user_setting){
                    toolbar.setTitle(R.string.menu_user_setting);
                    loadFragment(new userSettingFragment());
                }

                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        getMovieList();
    }

    public void getMovieList(){
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        //인터넷 연결이 되어있다면
        if(networkInfo != null){

            Toast.makeText(this, "인터넷에 연결되어 있습니다.", Toast.LENGTH_SHORT).show();

            String url = Api.movieListUrl + "?type=1";

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
                            Log.e("ERROR", "Error occurred ", error);
                        }
                    }
            );
            request.setShouldCache(false);
            AppHelper.requestQueue.add(request);

        }
        //인터넷에 연결이 안 되어있다면
        else {
            Toast.makeText(this, "인터넷에 연결되어 있지 않습니다. DB 에서 데이터를 불러옵니다.", Toast.LENGTH_SHORT).show();
            readMovieListFromDB();
        }
    }

    public void readMovieListFromDB(){
        if(helper != null){
            database = helper.getReadableDatabase();

            String sql = "select * from MovieList";
            Cursor c = database.rawQuery(sql, null);

            for(int i=0; i<c.getCount(); i++){
                c.moveToNext();
                int _id = c.getInt(0);
                int id = c.getInt(1);
                String title = c.getString(2);
                String date = c.getString(3);
                Float user_rating = c.getFloat(4);
                Float audience_rating = c.getFloat(5);
                Float reviewer_rating = c.getFloat(6);
                Float reservation_rate = c.getFloat(7);
                int reservation_grade = c.getInt(8);
                int grade = c.getInt(9);
                String imagePath = c.getString(10);
                HomeFragment.arrayList.add(new Movie(id,title,date,user_rating,audience_rating,reviewer_rating,reservation_rate,reservation_grade,grade,imagePath));
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new HomeFragment()).commit();
        }
    }

    public void insertMovieListIntoDB(ArrayList<Movie> result){
        if(helper != null){
            for (int i = 0; i < result.size(); i++) {
                final int id = result.get(i).id;
                final String title = result.get(i).title;

                String find_id = "select id" + " from MovieList where id = "+id;
                Cursor c = database.rawQuery(find_id , null);
                if(c.getCount() > 0) continue;

                Glide.with(this).asBitmap().load(result.get(i).image).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        saveFile(getApplicationContext(), resource, id, title, true);
                    }
                });

                ContentValues values = new ContentValues();
                values.put("id", result.get(i).id);
                values.put("title", result.get(i).title);
                values.put("date", result.get(i).date);
                values.put("user_rating", result.get(i).user_rating);
                values.put("audience_rating", result.get(i).audience_rating);
                values.put("reviewer_rating", result.get(i).reviewer_rating);
                values.put("reservation_rate", result.get(i).reservation_rate);
                values.put("reservation_grade", result.get(i).reservation_grade);
                values.put("grade", result.get(i).grade);
                database.insert("MovieList", null, values);
            }
        }
    }

    public static void saveFile(Context context, Bitmap file, int id, String title, boolean image){
        File localFile = new File(context.getFilesDir(), title + ".png");
        String filePath = "";

        try{
            localFile.createNewFile();
            filePath = localFile.getPath();
            FileOutputStream out = new FileOutputStream(localFile);
            file.compress(Bitmap.CompressFormat.PNG,100,out);
            out.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        if(image){
            String sql = "UPDATE MovieList SET image = \""+ filePath+"\" where id = "+id;
            database.execSQL(sql);
        }
        else{
            String sql = "UPDATE MovieInfo SET thumb = \""+ filePath+"\" where id = "+id;
            database.execSQL(sql);
        }
    }

    public void processResponse(String response){
        Gson gson = new Gson();
        MovieList movieList = gson.fromJson(response, MovieList.class);

        if(movieList.code == 200) {
            HomeFragment.arrayList.addAll(movieList.result);
            insertMovieListIntoDB(movieList.result);
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment()).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void loadFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();
    }

    @Override
    public void onDetailClicked(int id) {
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        final MovieDetailFragment newFragment = new MovieDetailFragment();

        if(networkInfo != null) {
            String url = Api.movieDetailUrl + id;
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Gson gson = new Gson();
                            MovieDetail movieDetail = gson.fromJson(response, MovieDetail.class);

                            if (movieDetail.code == 200) {
                                MovieInfo movieInfo = movieDetail.result.get(0);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("movieInfo", movieInfo);
                                newFragment.setArguments(bundle);

                                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, newFragment).addToBackStack(null).commit();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("ERROR", "Error occurred ", error);
                        }
                    }
            );

            request.setShouldCache(false);
            AppHelper.requestQueue.add(request);
        }
        else {
            Bundle bundle = new Bundle();
            bundle.putInt("id", id);
            newFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, newFragment).addToBackStack(null).commit();
        }
    }


}
