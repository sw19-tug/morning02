package com.gallery.android.gallery;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.text.SimpleDateFormat;

public class ImageContainer implements ImageContainerInterface{
    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public SimpleDateFormat getDate() {
        return date;
    }

    public void setDate(SimpleDateFormat date) {
        this.date = date;
    }

    private Bitmap image;
    private String path;
    private SimpleDateFormat date;

    public ImageContainer()
    {
        image = null;
        path = "";
        date = new SimpleDateFormat();
    }

    public ImageContainer(String path)
    {
        image = BitmapFactory.decodeFile(path);
        path = path;
        date = new SimpleDateFormat();
    }
}