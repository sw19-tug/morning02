package com.gallery.android.gallery;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class PictureDummy {


    Date date;

    String filename;

    int size;


    PictureDummy(Date date, String filename, int size) {
        this.date = date;
        this.filename = filename;
        this.size = size;
    }


    Date getDate() {

        return date;
    }

    String getFilename() {
        return filename;
    }

    int getSize() {
        return size;
    }




}
