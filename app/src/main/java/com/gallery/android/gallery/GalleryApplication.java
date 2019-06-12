package com.gallery.android.gallery;

import android.app.Application;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class GalleryApplication extends Application {

        public ArrayList<ImageContainer> imgs = new ArrayList<>();

        public List<Tags> tags = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        tags.addAll(createTagsList());
    }

    public  ArrayList<Tags> createTagsList() {
        ArrayList<Tags> tagsList = new ArrayList<Tags>();

        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        String jsonString = prefs.getString("tags", "");

        if(jsonString.equals(""))
        {
            String basic_tags[] = {"T1", "T2", "T3", "T4", "T5"};
            for (int i = 0; i < basic_tags.length; i++) {
                tagsList.add(new Tags(basic_tags[i]));
            }
            return tagsList;
        }

        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                tagsList.add(new Tags(jsonArray.getString(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tagsList;
    }

}
