package com.gallery.android.gallery;

import android.nfc.Tag;

import java.util.ArrayList;

public class Tags {

    private String mName;

    private Integer tagid;

    public Tags(String name) {
        mName = name;
        tagid = ++lastTagId;
    }

    public String getName() {
        return mName;
    }

    private static int lastTagId = 0;


    public static ArrayList<Tags> createTagsList() {
        ArrayList<Tags> contacts = new ArrayList<Tags>();

        String basic_tags[] = {"T1", "T2", "T3", "T4", "T5"};

        for (int i = 0; i < basic_tags.length; i++) {
            contacts.add(new Tags(basic_tags[i]));
        }

        return contacts;
    }
}
