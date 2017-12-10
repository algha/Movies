package com.yousef.movies.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yousef.movies.R;

/**
 * Created by yousef on 4/25/2017.
 */


public class FragmentMe extends Fragment {

    public FragmentMe(){};

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_me, container, false);

    }

}