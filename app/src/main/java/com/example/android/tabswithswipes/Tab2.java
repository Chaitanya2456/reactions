package com.example.android.tabswithswipes;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.android.tabswithswipes.adapter.shareFeedAdapter;

import com.example.android.tabswithswipes.Utils.CircleProgressBarDrawable;
import com.example.android.tabswithswipes.feed.shareFeed;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Chaitanya Shiva on 08-05-2018.
 */

public class Tab2 extends Fragment{
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private shareFeedAdapter adapter;
    private List<shareFeed> shareFeedList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.tab2, container, false);

        shareFeedList = new ArrayList<>();
        adapter = new shareFeedAdapter(getContext(), shareFeedList);
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView = (RecyclerView) v.findViewById(R.id.shareRecyclerView);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        shareFeed share_feed = new shareFeed("Poll", "Ask a Yes or No question", R.drawable.poll_icon);
        shareFeedList.add(0, share_feed);
        share_feed = new shareFeed("Cinema Buddy", "Ask anyone up for watching a movie", R.drawable.cinema_icon);
        shareFeedList.add(0, share_feed);
        share_feed = new shareFeed("Invite", "Invite your buddies for a party", R.drawable.invite_icon);
        shareFeedList.add(0, share_feed);
        share_feed = new shareFeed("Collab", "Ask your friends for a collboration", R.drawable.colab_icon);
        shareFeedList.add(0, share_feed);
        share_feed = new shareFeed("Fetch", "Shout out to your friends to fetch you few things", R.drawable.fetch_icon);
        shareFeedList.add(0, share_feed);
        share_feed = new shareFeed("Rate it", "Post anything and get ratings from your friends", R.drawable.rate_icon);
        shareFeedList.add(0, share_feed);
        share_feed = new shareFeed("Question", "Ask your friends questions or suggestions", R.drawable.question_icon);
        shareFeedList.add(0, share_feed);
        share_feed = new shareFeed("My Top 10", "Share with your friends a Top 10 list of things such as movies, places etc.", R.drawable.top10_icon);
        shareFeedList.add(0, share_feed);
        Collections.reverse(shareFeedList);
        adapter.notifyDataSetChanged();

        return v;
    }

    private boolean checkWriteExternalPermission(){
        String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            int res = getContext().checkCallingOrSelfPermission(permission);
            return (res== PackageManager.PERMISSION_GRANTED);}
        else return true;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
