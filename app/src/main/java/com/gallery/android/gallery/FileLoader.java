package com.gallery.android.gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Environment;
import android.util.Pair;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileLoader implements FileLoaderInterface {
    public FileLoader(){
        this.bitmapList=new ArrayList<Bitmap>();
        this.extentions=new ArrayList<String>();
        this.extentions.add("jpg");
        this.extentions.add("png");
        this.extentions.add("bmp");
        this.extentions.add("gif");
        this.extentions.add("jpeg");
    }
    private List<Bitmap> bitmapList;
    private List<String> extentions;

    private void search(File[] filelist, List<String> paths, boolean include_subfolders) {
        for (File f : filelist) {
            for (String extension : extentions) {
                if(f.getName().startsWith(".")) {
                    break;
                }
                if (f.getName().endsWith("." + extension)) {
                    paths.add(f.getAbsolutePath());
                    break;
                }
                if (f.isDirectory()) {
                    if (include_subfolders) {
                        File[] newfilelist = f.listFiles();
                        search(newfilelist, paths, true);
                        break;
                    } else {
                        break;
                    }
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


    public ArrayList<String> getImagesInformation() {
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();

        File dir = new File(path);
        File[] filelist = dir.listFiles();
        boolean success = false;
        if(!dir.exists() && !dir.isDirectory()){
            success=dir.mkdirs();
            if(success)
                filelist = dir.listFiles();
        }
        ArrayList<String> paths = new ArrayList<String>();
        if(filelist != null)
            search(filelist, paths, true);
        return paths;
    }

    public ArrayList<String> getImagesInformationForPath(String path, boolean include_subfolders) {

        File dir = new File(path);
        File[] filelist = dir.listFiles();
        boolean success = false;
        if(!dir.exists() && !dir.isDirectory()){
            success=dir.mkdirs();
            if(success)
                filelist = dir.listFiles();
        }
        ArrayList<String> paths = new ArrayList<String>();
        if(filelist != null) {
            search(filelist, paths, include_subfolders);
        }
        return paths;
    }


    public ArrayList<ImageContainer> loadImageContainers(Context context){

        ArrayList<String> paths = this.getImagesInformation();

        MediaStoreDataLoader loader = new MediaStoreDataLoader(context);

        return loader.parseAllImages(paths);
    }

    public ArrayList<Pair<String, Bitmap>> loadAlbums(){
        ArrayList<String> imagePaths = this.getImagesInformation();
        ArrayList<Pair<String, Bitmap>> albumPaths=new ArrayList<Pair<String, Bitmap>>();
        for(String image_path : imagePaths) {
            String path= image_path.substring(0,image_path.lastIndexOf("/"));

            boolean found = false;

            for (Pair<String, Bitmap> album : albumPaths)
            {
                if (album.first.equals(path))
                {
                    found = true;
                    break;
                }
            }

            if (!found){
                Bitmap thumbnail = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(image_path),
                        256, 256);
                albumPaths.add(Pair.create(path, thumbnail));
            }
        }
        return albumPaths;
    }

    public ArrayList<ImageContainer> loadImageContainersForPath(String album_path, boolean include_subfolders, Context context) {
        ArrayList<String> paths = this.getImagesInformationForPath(album_path, include_subfolders);

        MediaStoreDataLoader loader = new MediaStoreDataLoader(context);

        return loader.parseAllImages(paths);
    }
}