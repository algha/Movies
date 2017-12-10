package com.yousef.movies.Fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yousef.movies.ActivityMovies;
import com.yousef.movies.R;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yousef on 4/25/2017.
 */

public class FragmentMovieHome extends Fragment {

    View view;
    List<List<String>> mDataList;

    List<String> titles;

    private RecyclerView mVerticalList;

    private Context context;



    // newInstance constructor for creating fragment with arguments
    public static FragmentMovieHome newInstance(int page, String title) {
        FragmentMovieHome fragmentFirst = new FragmentMovieHome();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_movie_home, container, false);
        prepareData();
        initListView();
        return view;
    }

    private void prepareData() {

        titles = new ArrayList<String>();
        titles.add("Movies");
        titles.add("TV Shows");
        titles.add("Episodes");
        titles.add("Music");
        titles.add("Game");

        mDataList = new ArrayList<>();
        int vItemCount = 5;
        int hItemCount = 15;
        for (int i = 0; i < vItemCount; i++) {
            List<String> hList = new ArrayList<>();
            for (int j = 0; j < hItemCount; j++) {
                hList.add("Item." + j);
            }
            mDataList.add(hList);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:

                break;

            default:
        }
        return super.onOptionsItemSelected(item);
    }

    private void initListView() {
        mVerticalList = (RecyclerView) view.findViewById(R.id.vertical_list);
        mVerticalList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        VerticalAdapter verticalAdapter = new VerticalAdapter();
        verticalAdapter.setData(mDataList,context);
        mVerticalList.setAdapter(verticalAdapter);
    }



    private static class VerticalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<List<String>> mDataList;
        private Context context;


        public VerticalAdapter() {

        }

        public void setData(List<List<String>> data, Context mContext) {
            mDataList = data;
            context = mContext;
            notifyDataSetChanged();
        }

        private class HorizontalListViewHolder extends RecyclerView.ViewHolder {

            private TextView title;
            private TextView more;
            private RecyclerView horizontalList;
            private HorizontalAdapter horizontalAdapter;


            public HorizontalListViewHolder(View itemView) {
                super(itemView);
                Context context = itemView.getContext();
                title = (TextView) itemView.findViewById(R.id.item_title);
                more = (TextView) itemView.findViewById(R.id.more);
                horizontalList = (RecyclerView) itemView.findViewById(R.id.item_horizontal_list);
                horizontalList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                horizontalAdapter = new HorizontalAdapter();
                horizontalList.setAdapter(horizontalAdapter);
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            View itemView = LayoutInflater.from(context).inflate(R.layout.vertical_list_item, parent, false);
            HorizontalListViewHolder holder = new HorizontalListViewHolder(itemView);
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder rawHolder, int position) {
            HorizontalListViewHolder holder = (HorizontalListViewHolder) rawHolder;
            holder.title.setText("Movies");
            holder.horizontalAdapter.setData(mDataList.get(position));
            holder.horizontalAdapter.setRowIndex(position);
            holder.more.setTag("movie");
            holder.more.setOnClickListener(new  View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ActivityMovies.class);
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }
    }



    private static class HorizontalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<String> mDataList;
        private int mRowIndex = -1;
        private Context mContext;

        public HorizontalAdapter() {
        }

        public void setData(List<String> data) {
            if (mDataList != data) {
                mDataList = data;
                notifyDataSetChanged();
            }
        }

        public void setRowIndex(int index) {
            mRowIndex = index;
        }

        private class ItemViewHolder extends RecyclerView.ViewHolder {

            private TextView text;
            private ImageView image;

            public ItemViewHolder(View itemView) {
                super(itemView);
                text = (TextView) itemView.findViewById(R.id.name);
                image = (ImageView) itemView.findViewById(R.id.movie_image);
                itemView.setOnClickListener(mItemClickListener);
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            mContext = context;
            View itemView = LayoutInflater.from(context).inflate(R.layout.horizontal_list_item, parent, false);
            ItemViewHolder holder = new ItemViewHolder(itemView);
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder rawHolder, int position) {
            ItemViewHolder holder = (ItemViewHolder) rawHolder;
            holder.itemView.setTag(position);
            holder.image.setImageResource(R.mipmap.ic_launcher);
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage("http://www.best10movies.com/uploads/common/2017/04/22/97db0f4f5047922aa835943093b1cc0d.jpg", holder.image);

        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }

        private View.OnClickListener mItemClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int columnIndex = (int) v.getTag();
                int rowIndex = mRowIndex;

                String text = String.format("rowIndex:%d ,columnIndex:%d", rowIndex, columnIndex);
            }
        };
    }


}