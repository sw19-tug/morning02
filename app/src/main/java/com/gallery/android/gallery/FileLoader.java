package com.gallery.android.gallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileLoader implements FileLoaderInterface {
    public FileLoader(){
        this.bitmapList=new ArrayList<Bitmap>();
    }
    private List<Bitmap> bitmapList;




}
