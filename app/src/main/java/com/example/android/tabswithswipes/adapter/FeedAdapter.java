package com.example.android.tabswithswipes.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.example.android.tabswithswipes.R;
import com.example.android.tabswithswipes.feed.Feed;

import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.MyViewHolder> {
    private Context mContext;
    private List<Feed> feedList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, location, description, timeStamp;
        public ImageView image;

        public MyViewHolder(View view){
            super(view);
            name = (TextView) view.findViewById(R.id.cardView_name);
            location = (TextView) view.findViewById(R.id.cardView_location);
            description = (TextView) view.findViewById(R.id.cardView_description);
            timeStamp = (TextView) view.findViewById(R.id.cardView_timestamp);
            image = (ImageView) view.findViewById(R.id.cardView_image);
        }
    }

    public FeedAdapter(Context mContext, List<Feed> feedList) {
        this.mContext = mContext;
        this.feedList = feedList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_feed, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        final Feed feed = feedList.get(position);
        holder.name.setText(feed.getName());
        holder.location.setText(feed.getLocation());
        holder.description.setText(feed.getDescription());
        holder.timeStamp.setText(feed.getTimeStamp());

        Glide.with(mContext).load(feed.getImageUrl())
                .override(1280, 720).fitCenter()
                .priority(Priority.LOW).into(holder.image);
    }

    @Override
    public int getItemCount(){
        return feedList.size();
    }

    @Override
    public long getItemId(int position){
        return super.getItemId(position);
    }
}
