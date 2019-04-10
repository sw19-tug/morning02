package com.gallery.android.gallery;

import java.io.File;

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
        }
        return deleted;
    }
}
