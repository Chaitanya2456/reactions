package com.example.android.tabswithswipes.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.tabswithswipes.Friends;
import com.example.android.tabswithswipes.FriendsProfile;
import com.example.android.tabswithswipes.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.MyViewHolder> {
    private Context mContext;
    private List<Friends> friendsList;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public SimpleDraweeView friendsProfileImage;
        public TextView friendsUsername;
        public LinearLayout parentLayout;

        public MyViewHolder(View view){
            super(view);
            friendsProfileImage = (SimpleDraweeView)view.findViewById(R.id.friend_image);
            friendsUsername = (TextView)view.findViewById(R.id.friend_user_name);
            parentLayout = (LinearLayout)view.findViewById(R.id.friends_list_layout);
        }
    }

    public FriendsAdapter (Context mContext, List<Friends> friendsList){
        this.mContext = mContext;
        this.friendsList = friendsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friends_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        final Friends friends = friendsList.get(position);
        holder.friendsProfileImage.setImageURI(friends.getProfile_photo());
        holder.friendsUsername.setText(friends.getUsername());
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, FriendsProfile.class);
                intent.putExtra("friend_userName", friends.getUsername());
                intent.putExtra("friend_profileImage", friends.getProfile_photo());
                intent.putExtra("friend_name", friends.getName());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount(){
        return friendsList.size();
    }

    @Override
    public long getItemId(int position){
        return super.getItemId(position);
    }
}
