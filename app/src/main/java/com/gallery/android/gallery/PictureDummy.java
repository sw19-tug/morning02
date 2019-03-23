package com.gallery.android.gallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.text.SimpleDateFormat;

public class PictureDummy {

    private Bitmap image;
    private String path;
    private SimpleDateFormat date;
    private int image_res;

    public PictureDummy(int image_res)
    {
        this.image_res = image_res;
    }
}
