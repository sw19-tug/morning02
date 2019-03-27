package com.gallery.android.gallery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Date date = new Date();
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = date_format.parse("2018-04-05");


            PictureDummy p1 = new PictureDummy(date, "A", 15);
            PictureDummy p2 = new PictureDummy(date, "B", 19);
            PictureDummy p3 = new PictureDummy(date, "C", 10);
            PictureDummy p4 = new PictureDummy(date, "B", 12);

            SortablePictureList sortlist = new SortablePictureList();
            sortlist.pictures.add(p1);
            sortlist.pictures.add(p2);
            sortlist.pictures.add(p3);
            sortlist.pictures.add(p4);

            sortlist.sortByName(SortablePictureList.SortOrder.ASCENDING);


        }
        catch (ParseException ex1) {

        }


    }
}
