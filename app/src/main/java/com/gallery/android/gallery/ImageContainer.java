package com.gallery.android.gallery;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.nio.file.Path;
import java.nio.file.Paths;
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

    public String getFilename(){return filename;}

    public void setFilename(String name){this.filename = name;}

    private Bitmap image;
    private String path;
    private SimpleDateFormat date;
    private String filename;

    public ImageContainer()
    {
        image = null;
        path = "";
        date = new SimpleDateFormat();
    }

    public ImageContainer(String path)
    {
        this.image = BitmapFactory.decodeFile(path);
        this.path = path;
        this.date = new SimpleDateFormat();
        this.filename = path.substring(path.lastIndexOf("/")+1);
    }

    //for testing purposes
    public ImageContainer(String thepath,SimpleDateFormat thedate, String thename)
    {
        path = thepath;
        date = thedate;
        filename = thename;
    }
}
