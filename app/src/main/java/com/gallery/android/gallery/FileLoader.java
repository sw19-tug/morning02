package com.gallery.android.gallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileLoader implements FileLoaderInterface {
    public FileLoader(){
        this.bitmapList=new ArrayList<Bitmap>();
        this.extentions=new ArrayList<String>();
        this.extentions.add("jpg");
        this.extentions.add("png");
        this.extentions.add("bmp");
        this.extentions.add("gif");
    }
    private List<Bitmap> bitmapList;
    private List<String> extentions;

    private void search(File[] filelist, List<String> paths) {
        for (File f : filelist) {
            for (String extetion : extentions) {
                if(f.getName().startsWith(".")) {
                    break;
                }
                if (f.getName().endsWith("." + extetion)) {
                    paths.add(f.getAbsolutePath());
                    break;
                }
                if (f.isDirectory()) {
                    File[] newfilelist = f.listFiles();
                    search(newfilelist, paths);
                    break;
                }
            }
        }
    }

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


    public List<String> getImagesInformation() {
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();

        File dir = new File(path);
        File[] filelist = dir.listFiles();
        boolean success = false;
        if(!dir.exists() && !dir.isDirectory()){
            success=dir.mkdirs();
            if(success)
                filelist = dir.listFiles();
        }
        List<String> paths = new ArrayList<String>();
        if(filelist != null)
            search(filelist, paths);
        return paths;
    }


    public ArrayList<ImageContainer> loadImageContainers(){
        List<String> paths=this.getImagesInformation();
        ArrayList<ImageContainer> imageList = new ArrayList<ImageContainer>();
        for(int i = 0; i < paths.size(); i++ )
        {
            ExifInterface exif = null;
            try {
                 exif = new ExifInterface(paths.get(i));
            } catch (IOException e) {
                e.printStackTrace();
            }
            File file = new File(paths.get(i));

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy:MM:dd hh:mm:ss");
            String datestring = exif.getAttribute(ExifInterface.TAG_DATETIME);
            try{
                Date date = simpleDateFormat.parse(datestring);
                imageList.add(new ImageContainer(paths.get(i), date, file.length()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            catch(NullPointerException n) {
                System.out.println("Datestring : " + datestring);
            }

        }
        return imageList;
    }
}