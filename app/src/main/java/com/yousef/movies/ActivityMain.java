package com.yousef.movies;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.yousef.movies.Fragments.FragmentMe;
import com.yousef.movies.Fragments.FragmentMoment;
import com.yousef.movies.Fragments.FragmentMovieHome;
import com.yousef.movies.Fragments.FragmentTop;

import java.lang.reflect.Field;

public class ActivityMain extends AppCompatActivity {

    Fragment indexFragment,timelineFragment,topFragment,meFragment;

    private FragmentManager fragmentManager = getSupportFragmentManager();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setTabSelection(0);
                    return true;
                case R.id.navigation_timeline:
                    setTabSelection(1);
                    return true;
                case R.id.navigation_hot:
                    setTabSelection(2);
                    return true;
                case R.id.navigation_me:
                    setTabSelection(3);
                    return true;
            }
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setElevation(0);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);

        setTabSelection(0);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigation.getChildAt(0);

        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e) {
        }
    }


    private void setTabSelection(int index) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        hideFragments(transaction);

        switch (index) {
            case 0:
                if (indexFragment == null) {
                    indexFragment = new FragmentMovieHome();
                    transaction.add(R.id.content, indexFragment);
                } else {
                    transaction.show(indexFragment);
                }
                break;
            case 1:
                if (timelineFragment == null) {
                    timelineFragment = new FragmentMoment();
                    transaction.add(R.id.content, timelineFragment);
                } else {
                    transaction.show(timelineFragment);
                }
                break;
            case 2:
                if (topFragment == null) {
                    topFragment = new FragmentTop();
                    transaction.add(R.id.content, topFragment);
                } else {
                    transaction.show(topFragment);
                }
                break;
            case 3:
                if (meFragment == null) {
                    meFragment = new FragmentMe();
                    transaction.add(R.id.content, meFragment);
                } else {
                    transaction.show(meFragment);
                }
                break;

        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (indexFragment != null) {
            transaction.hide(indexFragment);
        }
        if (timelineFragment != null) {
            transaction.hide(timelineFragment);
        }
        if (topFragment != null) {
            transaction.hide(topFragment);
        }
        if (meFragment != null) {
            transaction.hide(meFragment);
        }

    }

}
