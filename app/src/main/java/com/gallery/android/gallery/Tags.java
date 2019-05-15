package com.gallery.android.gallery;

import java.util.ArrayList;

public class Tags {

    private String mName;

    private Integer tagid;

    public Tags(String name, boolean b) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    private static int lastTagId = 0;

    public static ArrayList<Tags> createTagsList(int numTags) {
        ArrayList<Tags> contacts = new ArrayList<Tags>();

        for (int i = 1; i <= numTags; i++) {
            contacts.add(new Tags("Tag " + ++lastTagId, i <= numTags / 2));
            lastTagId++;
        }

        return contacts;
    }
}
