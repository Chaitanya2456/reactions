package com.example.android.tabswithswipes.Utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.tabswithswipes.R;

import java.util.ArrayList;

/**
 * Created by Chaitanya Shiva on 13-05-2018.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private ArrayList<String> imgURIs;
    private Context context;
    public MyRecyclerViewAdapter(Context context, ArrayList<String> imgURIs){
        this.imgURIs = imgURIs;
        this.context = context;
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, null);
        RecyclerViewHolder rcv = new RecyclerViewHolder(layoutView);
        return rcv;
    }
    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position){
        holder.rvImage.setImageURI(imgURIs.get(position));
    }
    @Override
    public int getItemCount(){
        return imgURIs.size();
    }
}
