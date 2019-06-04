package com.gallery.android.gallery;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class GalleryApplication extends Application {

        public List<ImageContainer> imgs;

        public List<Tags> tags = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        tags.addAll(createTagsList());
    }

    public static ArrayList<Tags> createTagsList() {
        ArrayList<Tags> tagsList = new ArrayList<Tags>();

        String basic_tags[] = {"T1", "T2", "T3", "T4", "T5"};

        for (int i = 0; i < basic_tags.length; i++) {
            tagsList.add(new Tags(basic_tags[i]));
        }

        return tagsList;
    }

}
