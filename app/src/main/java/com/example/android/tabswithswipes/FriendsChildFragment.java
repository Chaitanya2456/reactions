package com.example.android.tabswithswipes;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.tabswithswipes.adapter.FriendsAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class FriendsChildFragment extends Fragment {

    private ArrayList<Friends> returnedFriends;
    private RecyclerView recyclerView;
    private FriendsAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static final List<Friends> friendsList = new ArrayList<>();
    private static final HashSet<String> userSet = new HashSet<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        View v = (View) inflater.inflate(R.layout.friends_child_fragment, container, false);
        adapter = new FriendsAdapter(getContext(), friendsList);
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView = (RecyclerView)v.findViewById(R.id.friends_recycler_view);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        returnedFriends = FireBaseMethods.readFriends(new FirebaseCallback2(){

            @Override
            public void onCallback(ArrayList<Friends> returnedFriends) {
                Log.d("SIZE", String.valueOf(returnedFriends.size()));
                for(int i=0;i<returnedFriends.size();i++){
                    Log.d("FRIENDSH", returnedFriends.get(i).getUsername());
                    if(!userSet.contains(returnedFriends.get(i).getUsername())){
                        userSet.add(returnedFriends.get(i).getUsername());
                        friendsList.add(returnedFriends.get(i));
                    }
                }
            }


        });
        adapter.notifyDataSetChanged();
        return v;
    }
}
