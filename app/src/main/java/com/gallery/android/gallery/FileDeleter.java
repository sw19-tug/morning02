package com.gallery.android.gallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileDeleter implements FileDeleterInterface{
    public FileDeleter(){}

    @Override
    public boolean delete(String path) {

        boolean deleted = false;
        File file = new File(path);
        if(file.exists())
        {
            deleted = file.delete();
            if(deleted)
                System.out.println("File deleted: " + path);
            else
                System.out.println("File could not be deleted: " + path);
        }
        else
        {
            System.out.println("File not found: " + path);
            deleted = false;
        }

        return deleted;
    }

}
