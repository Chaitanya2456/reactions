package com.example.android.tabswithswipes;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.tabswithswipes.Utils.CircleProgressBarDrawable;
import com.example.android.tabswithswipes.Utils.FilePaths;
import com.example.android.tabswithswipes.Utils.FileSearch;
import com.example.android.tabswithswipes.Utils.GridImageAdapter;
import com.example.android.tabswithswipes.Utils.Permissions;
import com.example.android.tabswithswipes.Utils.UniversalImageLoader;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.view.SimpleDraweeView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Chaitanya Shiva on 08-05-2018.
 */

public class GalleryFragment extends android.support.v4.app.FragmentActivity {
    private static final String TAG = "GalleryFrgment";
    private GridView gridView;
    private SimpleDraweeView galleryImage;
    private Spinner directorySpinner;
    private Context mContext = GalleryFragment.this;
    private String mSelectedImage;
    private ArrayList<String> directories;
    private String mAppend = "file:/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_gallery);
        final boolean requestTab1 = this.getIntent().hasExtra("SenderKeyTab1");
        final boolean requestTab2  = this.getIntent().hasExtra("SenderKeyTab2");
        galleryImage = (SimpleDraweeView)findViewById(R.id.galleryImageView);
        galleryImage.getHierarchy().setProgressBarImage(new CircleProgressBarDrawable());
        gridView = (GridView)findViewById(R.id.gridView);
        directorySpinner = (Spinner)findViewById(R.id.spinnerDirectory);
        ImageView closeGallery = (ImageView)findViewById(R.id.closeGallery);
        closeGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        init();
        TextView nextScreen = (TextView) findViewById(R.id.tvNext);
        nextScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(requestTab1){
                    SharedPreferences preferences = getSharedPreferences("MyPref",0);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("imgUriTab1",mSelectedImage);
                    editor.commit();
                    finish();}
                    if(requestTab2){
                        SharedPreferences preferences = getSharedPreferences("MyPref",0);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("imgUriTab2",mSelectedImage);
                        editor.commit();
                        finish();
                    }
            }
        });
    }

    private void init(){
        FilePaths filePaths = new FilePaths();
        if(FileSearch.getDirectoryPaths(filePaths.PICTURES)!=null){
            directories = FileSearch.getDirectoryPaths(filePaths.PICTURES);
        }
        directories.add(filePaths.CAMERA);
        ArrayList<String> directoryNames = new ArrayList<>();
        for(int i=0;i<directories.size();i++){
            int index = directories.get(i).lastIndexOf("/");
            String string = directories.get(i).substring(index).replace("/","");
            directoryNames.add(string);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, directoryNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        directorySpinner.setAdapter(adapter);

        directorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setUpGridView(directories.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private void setUpGridView(String selectedDirectory) {
        final ArrayList<String> imgURLs = FileSearch.getFilePaths(selectedDirectory);
        Collections.reverse(imgURLs);
        GridImageAdapter adapter = new GridImageAdapter(mContext, R.layout.layout_grid_imageview, mAppend, imgURLs);
        gridView.setAdapter(adapter);
        if (imgURLs != null && imgURLs.size() > 0) {
            try {
                galleryImage.setImageURI(Uri.fromFile(new File(imgURLs.get(0))));
                mSelectedImage = imgURLs.get(0);
            } catch (ArrayIndexOutOfBoundsException e) {
                Log.e(TAG, "setUpGridView:ArrayIndexOutOfBounds: " + e.getMessage());
            }
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    galleryImage.setImageURI(Uri.fromFile(new File(imgURLs.get(position))));
                    mSelectedImage = imgURLs.get(position);
                }
            });
        }
    }



}