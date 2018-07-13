package com.example.android.tabswithswipes.Utils;

import android.os.Environment;

import static android.os.Environment.DIRECTORY_DCIM;
import static android.os.Environment.DIRECTORY_PICTURES;

/**
 * Created by Chaitanya Shiva on 14-05-2018.
 */

public class FilePaths {
    public String ROOT_DIR = Environment.getExternalStorageDirectory().getPath();
    public String PICTURES = Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES).getPath();
    public String CAMERA = Environment.getExternalStoragePublicDirectory(DIRECTORY_DCIM).getAbsolutePath() + "/Camera";
    public String STORIES = ROOT_DIR + "/Stories";
}
