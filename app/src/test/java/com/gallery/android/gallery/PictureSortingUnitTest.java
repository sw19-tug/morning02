package com.gallery.android.gallery;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.hasProperty;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.jar.JarException;

public class PictureSortingUnitTest {

    @Test
    public void hasMemberVariables() throws ParseException
    {
        //List<dummyimageclass> imageList = new ArrayList<>();
        Date date = new Date();
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        date = date_format.parse("2018-04-05");

        PictureDummy image = new PictureDummy(date, "test1", 5000);
        assertTrue(image.getFilename().equals("test1"));
        assertTrue(image.getSize() == 5000);


    }

}
