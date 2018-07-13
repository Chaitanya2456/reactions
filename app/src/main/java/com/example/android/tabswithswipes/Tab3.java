package com.example.android.tabswithswipes;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

/**
 * Created by qawbecrdteyf on 08-05-2018.
 */

public class Tab3 extends Fragment{
    private TextView userName;
    private Button editProfile;
    private FirebaseAuth auth;
    private ImageView logout, friendsImage, activityImage;
    private TextView fullName;
    private TextView userBio;
    private TextView emailiduser;
    private SimpleDraweeView userImage;
    private String[] returnedString2;
    private ArrayList<Friends> returnedFriends;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final String [] returnedvalues2 = new String[5];
        returnedString2 = FireBaseMethods.readData(new FirebaseCallback() {

            @Override
            public void onCallback(String[] returnedvalues2) {
                //Log.d("fghy",returnedvalues2[0]);
                userBio.setText(returnedvalues2[0]);
                emailiduser.setText(returnedvalues2[1]);
                fullName.setText(returnedvalues2[2]);
                //userImage.setImageURI(Uri.parse(returnedvalues2[3]));
                userName.setText(returnedvalues2[4]);
                SharedPreferences preferences = getActivity().getSharedPreferences("MyPref", 0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("user_bio", returnedvalues2[0]);
                editor.putString("email_id", returnedvalues2[1]);
                editor.putString("full_name", returnedvalues2[2]);
                editor.putString("user_image", returnedvalues2[3]);
                editor.putString("user_name", returnedvalues2[4]);
                editor.commit();
                Log.d("prefs", "entering data prefs");
            }

        });

        //Log.d("TINDER-CHAITANYA", returnedString);


        View v = inflater.inflate(R.layout.tab3, container, false);
        emailiduser = (TextView)v.findViewById(R.id.emailId);
        logout = (ImageView)v.findViewById(R.id.signOut);
        userName = (TextView)v.findViewById(R.id.userName);
        fullName = (TextView)v.findViewById(R.id.fullName);
        userBio = (TextView) v.findViewById(R.id.bioTextField);
        editProfile = (Button)v.findViewById(R.id.editProfile);
        friendsImage = (ImageView)v.findViewById(R.id.friendsFragment);
        activityImage = (ImageView)v.findViewById(R.id.activityFragment);
        userImage = (SimpleDraweeView) v.findViewById(R.id.profile_image);
        //Log.d("namein tab3", returnedvalues2[0]);

        //userBio.setText(returnedvalues2[1]);
        //userImage.setImageURI(Uri.parse(returnedvalues2[2]));


        auth = FirebaseAuth.getInstance();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("LOGGING OUT");
                alertDialog.setMessage("Are you sure you want to log out?");
                alertDialog.setIcon(R.drawable.logout);
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        auth.signOut();
                        startActivity(new Intent(getActivity(), loginActivity.class));
                        getActivity().finish();
                    }
                });
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                alertDialog.show();
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), EditProfileActivity.class));
            }
        });

        android.support.v4.app.FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.child_fragment_layout, new FriendsChildFragment());
        transaction.commit();

        activityImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityImage.setImageResource(R.drawable.activity_selected);
                friendsImage.setImageResource(R.drawable.friends_unselected);
                android.support.v4.app.FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.child_fragment_layout, new ActivityChildFragment());
                transaction.commit();

            }
        });

        friendsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                friendsImage.setImageResource(R.drawable.friends_selected);
                activityImage.setImageResource(R.drawable.activity_unselected);
                android.support.v4.app.FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.child_fragment_layout, new FriendsChildFragment());
                transaction.commit();
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
      SharedPreferences preferences = getActivity().getSharedPreferences("MyPref", 0);
        if(preferences.contains("user_bio")) {userBio.setText(preferences.getString("user_bio", ""));
         Log.d("in resume", preferences.getString("user_bio", "nothing"));}
        if(preferences.contains("email_id")) emailiduser.setText(preferences.getString("email_id", ""));
        if(preferences.contains("full_name")) fullName.setText(preferences.getString("full_name", ""));
        if(preferences.contains("user_image")) userImage.setImageURI(preferences.getString("user_image", ""));
        if(preferences.contains("user_name")) userName.setText(preferences.getString("user_name", ""));



    }
}
