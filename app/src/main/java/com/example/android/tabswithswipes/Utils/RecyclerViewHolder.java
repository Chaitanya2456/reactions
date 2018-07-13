package com.example.android.tabswithswipes.Utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.android.tabswithswipes.R;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by Chaitanya Shiva on 13-05-2018.
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public SimpleDraweeView rvImage;
    public RecyclerViewHolder(View itemView){
        super(itemView);
        itemView.setOnClickListener(this);
        rvImage = (SimpleDraweeView) itemView.findViewById(R.id.rvDrawee);
    }
    @Override
    public void onClick(View view){

    }
}
