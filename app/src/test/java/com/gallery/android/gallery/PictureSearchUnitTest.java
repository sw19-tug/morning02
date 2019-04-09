package com.gallery.android.gallery;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class PictureSearchUnitTest {

    AdapterImages adapter;

    @Before
    public void initialize() {
        ArrayList<ImageContainer> imageList = new ArrayList<>();
        imageList.add(new ImageContainer("somepath",new Date(),"ducky"));
        imageList.add(new ImageContainer("somepath",new Date(),"donald duck"));
        imageList.add(new ImageContainer("somepath",new Date(),"tick"));
        imageList.add(new ImageContainer("somepath",new Date(),"trick"));
        imageList.add(new ImageContainer("somepath",new Date(),"track"));
        imageList.add(new ImageContainer("somepath",new Date(),"dagobert duck"));
        imageList.add(new ImageContainer("somepath",new Date(),"daisy duck"));
        imageList.add(new ImageContainer("somepath",new Date(),"gustav gans"));
        imageList.add(new ImageContainer("somepath",new Date(),"gundel gauckele"));
        imageList.add(new ImageContainer("somepath",new Date(),"oma duck"));

        adapter=new AdapterImages(imageList);
    }

    @Test
    public void findPicture(){

        ImageContainer image = adapter.searchPictures("trick");
        assertEquals(image.getFilename(),"trick");
    }





}
