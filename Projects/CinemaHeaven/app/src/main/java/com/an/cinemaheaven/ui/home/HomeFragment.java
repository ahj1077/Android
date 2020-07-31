package com.an.cinemaheaven.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.an.cinemaheaven.MoviePagerAdapter;
import com.an.cinemaheaven.MoviePosterFragment;
import com.an.cinemaheaven.R;
import com.an.cinemaheaven.data.Movie;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    public static ArrayList<Movie> arrayList = new ArrayList<Movie>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        ViewPager pager = (ViewPager) root.findViewById(R.id.vp_movie_list);
        pager.setOffscreenPageLimit(3);

        MoviePagerAdapter moviePagerAdapter = new MoviePagerAdapter(getChildFragmentManager());

        for(int i = 0; i < arrayList.size(); i++){
            MoviePosterFragment poster = new MoviePosterFragment(arrayList.get(i));
            moviePagerAdapter.addItem(poster);
        }

        pager.setAdapter(moviePagerAdapter);

        return root;
    }
}