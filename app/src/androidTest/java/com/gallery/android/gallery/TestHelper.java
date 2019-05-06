package com.gallery.android.gallery;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;

public class TestHelper {

    public static void createFile(String name) {
        createFile(name, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString());
    }

    public static void deleteFile(String name){
        deleteFile(name, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString());
    }

    public static void createFile(String name, String path) {
        File dir = new File(path);
        if(!dir.exists() && !dir.isDirectory()){
            if(!dir.mkdirs())
                System.out.println("ERROR: Not able to create a test image!");
        }
        File dest = new File(path, name);
        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        for(int x = 0; x < 100; x++){
            for(int y = 0; y < 100; y++){
                bitmap.setPixel(x, y, Color.rgb(2, 100, 56));
            }
        }
        try {
            FileOutputStream fos = new FileOutputStream(dest);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteFile(String name, String path){
        File file = new File(path, name);
        if(file.exists())
        {
            file.delete();
        }
    }
}