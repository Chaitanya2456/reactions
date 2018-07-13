package com.example.android.tabswithswipes.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.tabswithswipes.CreatePoll;
import com.example.android.tabswithswipes.R;
import com.example.android.tabswithswipes.feed.shareFeed;

import java.util.List;

public class shareFeedAdapter extends RecyclerView.Adapter<shareFeedAdapter.MyViewHolder> {
    private Context mContext;
    private List<shareFeed> shareFeedList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView cardTitle, cardDescription;
        public ImageView cardLogo;
        public LinearLayout parentLayout;

        public MyViewHolder(View view){
            super(view);
            cardTitle = (TextView)view.findViewById(R.id.card_title);
            cardDescription = (TextView)view.findViewById(R.id.card_description);
            cardLogo = (ImageView)view.findViewById(R.id.card_logo);
            parentLayout = (LinearLayout) view.findViewById(R.id.share_feed_layout);
        }
    }

    public shareFeedAdapter(Context mContext, List<shareFeed> shareFeedList){
        this.mContext = mContext;
        this.shareFeedList = shareFeedList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.share_card_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        final shareFeed share_feed = shareFeedList.get(position);
        holder.cardTitle.setText(share_feed.getTitle());
        holder.cardDescription.setText(share_feed.getDescription());
        holder.cardLogo.setImageResource(share_feed.getImgResource());
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(mContext, "You clicked this view", Toast.LENGTH_SHORT).show();
                if(share_feed.getTitle()=="Poll"){
                    Intent intent = new Intent(mContext, CreatePoll.class);
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount(){
        return shareFeedList.size();
    }

    @Override
    public long getItemId(int position){
        return super.getItemId(position);
    }
}

