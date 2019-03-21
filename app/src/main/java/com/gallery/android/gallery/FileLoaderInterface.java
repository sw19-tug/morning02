package com.gallery.android.gallery;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.util.List;

public interface FileLoaderInterface {

    Bitmap getAndAddBitMap(String s);
    ImageView getImages(ImageView view,int index);
    List<String> getImagesPaths();
}
