package com.yousef.movies.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yousef.movies.Layout.SlidingTabLayout;
import com.yousef.movies.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by yousef on 4/25/2017.
 */


public class FragmentIndex extends Fragment {

    private ArrayList<HashMap<String,Object>> pages;

    public FragmentIndex(){};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_index, container, false);

       setupData();

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.vp_view_pager);
        final SlidingTabLayout slidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tab_layout);


        MyPagerAdapter adapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.setData(pages);
        viewPager.setAdapter(adapter);

        //slidingTabLayout.setDistributeEvenly(true);     // set full width of screen
        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {    // nice when scroll if have some color
            @Override
            public int getIndicatorColor(int position) {
                if (position == 1)  // example
                    return getResources().getColor(R.color.colorPrimaryDark);
                return getResources().getColor(R.color.colorPrimaryDark);
            }
        });
        slidingTabLayout.setCustomTabView(R.layout.custom_layout, R.id.text_view);   // if want to set custom layout for each tab
        slidingTabLayout.setViewPager(viewPager);

        slidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return  view;

    }

    private void setupData(){
        pages = new ArrayList<HashMap<String,Object>>();
        HashMap<String, Object> all = new HashMap<String, Object>();
        all.put("Title","All");
        all.put("Type","All");
        pages.add(all);

        HashMap<String, Object> movies = new HashMap<String, Object>();
        movies.put("Title","Movies");
        movies.put("Type","movie");
        pages.add(movies);

        HashMap<String, Object> tv = new HashMap<String, Object>();
        tv.put("Title","TV Shows");
        tv.put("Type","movie");
        pages.add(tv);

        HashMap<String, Object> episode = new HashMap<String, Object>();
        episode.put("Title","Episode");
        episode.put("Type","movie");
        pages.add(episode);

        HashMap<String, Object> music = new HashMap<String, Object>();
        music.put("Title","Music");
        music.put("Type","video");
        pages.add(music);

        HashMap<String, Object> game = new HashMap<String, Object>();
        game.put("Title","Game");
        game.put("Type","video");
        pages.add(game);


    }




    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private LayoutInflater mInflater;

        private ArrayList<HashMap<String,Object>> data;

        public void setData(ArrayList<HashMap<String,Object>> mData){
            data = mData;
            notifyDataSetChanged();
        }

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return data.size();
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            HashMap<String,Object> indexData = data.get(position);
            String title = (String) indexData.get("Title");

            if (position == 0){
                return FragmentMovieHome.newInstance(position, title);
            }
            return FragmentMovies.newInstance(position, title);

        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            HashMap<String,Object> indexData = data.get(position);
            String title = (String) indexData.get("Title");

            return title;
        }

    }


}