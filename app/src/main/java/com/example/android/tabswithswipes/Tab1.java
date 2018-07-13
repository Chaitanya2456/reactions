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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.tabswithswipes.Utils.CircleProgressBarDrawable;
import com.example.android.tabswithswipes.Utils.UniversalImageLoader;
import com.example.android.tabswithswipes.adapter.FeedAdapter;
import com.example.android.tabswithswipes.feed.Feed;
import com.facebook.drawee.view.SimpleDraweeView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chaitanya Shiva on 08-05-2018.
 */

public class Tab1 extends Fragment{
   private RecyclerView recyclerView;
   private RecyclerView.LayoutManager mLayoutManager;
   private FeedAdapter adapter;
   private List<Feed> feedList;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab1, container, false);
        feedList = new ArrayList<>();
        adapter = new FeedAdapter(getContext(), feedList);
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        Feed feed = new Feed("Christopher Nolan","LA, California","https://static.seekingalpha.com/uploads/2016/4/21/saupload_The-Prestige-the-prestige-6899802-1280-1024.jpg",
                "Two friends and fellow magicians become bitter enemies after a sudden tragedy. " +
                        "As they devote themselves to this rivalry, they make sacrifices that bring them fame but with terrible consequences.",
                "2 DAYS AGO");
        feedList.add(0, feed);
        feed = new Feed("Christopher Nolan",
                "LA, California",
                "http://jonvilma.com/images/2010-inception-movie-3.jpg",
                "Cobb steals information from his targets by entering their dreams. " +
                        "He is wanted for his alleged role in his wife's murder and his only chance at redemption is to perform the impossible, an inception.",
                "1 DAY AGO");
        feedList.add(0, feed);
        feed = new Feed("Christopher Nolan",
                "LA, California",
                "https://s-media-cache-ak0.pinimg.com/originals/1b/34/fd/1b34fda96970d972d9bcfc14cdea99d9.jpg",
                "In the future, Earth is slowly becoming uninhabitable. Ex-NASA pilot Cooper, along with a team of researchers, " +
                        "is sent on a planet exploration mission to report which planet can sustain life.",
                "10 HOURS AGO");
        feedList.add(0, feed);

        feed = new Feed("Christopher Nolan",
                "LA, California",
                "https://occhimagazine.com/occhi/wp-content/uploads/2017/07/Dunkirk2-1024x576.jpg",
                "In May 1940, Germany advanced into France, trapping Allied troops on the beaches of Dunkirk." +
                        " At the end of this heroic mission, 330,000 French, British, Belgian and Dutch soldiers were safely evacuated.",
                "26 MINUTES AGO");
        feedList.add(0, feed);

        adapter.notifyDataSetChanged();

        return v;
    }

}
