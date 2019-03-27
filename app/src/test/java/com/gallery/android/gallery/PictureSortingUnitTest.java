package com.gallery.android.gallery;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.hasProperty;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.jar.JarException;

public class PictureSortingUnitTest {

    enum SortOrder{
        ASCENDING,DESCENDING
    }

    @Test
    public void hasMemberVariables() throws ParseException
    {
        //List<dummyimageclass> imageList = new ArrayList<>();
        Date date, date1, date2;
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        date = date_format.parse("2018-04-05");

        PictureDummy image = new PictureDummy(date, "test1", 5000);

        assertTrue(image.getFilename().equals("test1"));
        assertTrue(image.getSize() == 5000);

    }

    @Test
    public void inDecendingDateOrder() throws ParseException
    {

        List<PictureDummy> decendingimageList = new ArrayList<>();
        Date date, date1, date2;
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");

        date = date_format.parse("2018-08-07");
        date1 = date_format.parse("2018-04-05");
        date2 = date_format.parse("2008-01-04");

        PictureDummy image = new PictureDummy(date, "test", 5000);
        PictureDummy image1 = new PictureDummy(date1, "test1", 5000);
        PictureDummy image2 = new PictureDummy(date2, "test2", 5000);

        decendingimageList.add(0,image);
        decendingimageList.add(1,image1);
        decendingimageList.add(2,image2);

        SortablePictureList piclist = new SortablePictureList();

            piclist.pictures.add(image2);
            piclist.pictures.add(image);
            piclist.pictures.add(image1);

        piclist.sortByDate(false);//SortOrder.DESCENDING
        assertThat(piclist.pictures, is(equalTo(decendingimageList)));



    }

}
