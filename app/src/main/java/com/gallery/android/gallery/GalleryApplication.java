package com.gallery.android.gallery;

import android.app.Application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GalleryApplication extends Application {

    private Map<String, Object> mData;

        public ArrayList<ImageContainer> imgs = new ArrayList<>();

        public List<Tags> tags = new ArrayList<>();

    private static GalleryApplication sInstance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        tags.addAll(createTagsList());
        mData = new HashMap<String, Object>();
        mData.put("nightMode",new Boolean(false));
        sInstance=this;

    }

    public static ArrayList<Tags> createTagsList() {
        ArrayList<Tags> tagsList = new ArrayList<Tags>();

        String basic_tags[] = {"T1", "T2", "T3", "T4", "T5"};

        for (int i = 0; i < basic_tags.length; i++) {
            tagsList.add(new Tags(basic_tags[i]));
        }

        return tagsList;
    }
    public Object get(String key){
        return mData.get(key);
    }
    public void put(String key,Object value){
        mData.put(key, value);
    }
    public void set(String key,Object value){
        mData.put(key,value);
    }
    // Getter to access Singleton instance
    public static GalleryApplication getInstance() {
        return sInstance ;
    }

}
