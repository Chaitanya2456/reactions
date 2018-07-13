package com.example.android.tabswithswipes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

public class FriendsProfile extends AppCompatActivity {

    private TextView friendUserName, friendName, frindBio;
    private SimpleDraweeView friendImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_profile);
        friendUserName = (TextView)findViewById(R.id.friend_profile_userName);
        friendName = (TextView)findViewById(R.id.friend_profile_Name);
        frindBio = (TextView)findViewById(R.id.friend_profile_bio);
        friendImage = (SimpleDraweeView)findViewById(R.id.friend_profile_image);
        Intent intent = getIntent();
        friendUserName.setText(intent.getStringExtra("friend_userName"));
        friendName.setText(intent.getStringExtra("friend_name"));
        friendImage.setImageURI(intent.getStringExtra("friend_profileImage"));
    }
}
