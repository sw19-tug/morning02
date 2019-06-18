package com.gallery.android.gallery;

public class Tags {

    private String mName;

    private Integer tagid;
    private boolean checked = false;

    public Tags(String name) {
        mName = name;
        tagid = ++lastTagId;
    }

    public String getName() {
        return mName;
    }

    private static int lastTagId = 0;

    public int getTagId() {
        return tagid;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
