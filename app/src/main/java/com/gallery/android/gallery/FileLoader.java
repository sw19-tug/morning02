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
    @Override
    public Bitmap getAndAddBitMap(String s) {
        Bitmap b= BitmapFactory.decodeFile(s);
        bitmapList.add(b);
        return b;
    }

    @Override
    public ImageView getImages(ImageView view,int index) {
        view.setImageBitmap(bitmapList.get(index));
        return view;
    }
    public List<String> getImagesPaths() {
        //String path = Environment.getExternalStorageDirectory().toString()+"/Pictures";
        //String path = "/storage/sdcard/DCIM/Camera";
        //File dir = new File(path);
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File dir = new File(path, "/Camera");
        File[] filelist = dir.listFiles();
        List<String> paths = new ArrayList<String>();
        for (File f : filelist) {
            paths.add(f.getAbsolutePath());
        }
        return paths;
    }
    public ArrayList<ImageContainer> loadImageContainers(){
        List<String> paths=this.getImagesPaths();
        ArrayList<ImageContainer> imageList = new ArrayList<ImageContainer>();
        for(int i = 0; i < paths.size(); i++ )
        {
            imageList.add(new ImageContainer(paths.get(i)));
        }
        return imageList;
    }



}
