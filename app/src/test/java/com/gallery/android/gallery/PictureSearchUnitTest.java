package com.gallery.android.gallery;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class PictureSearchUnitTest {

    AdapterImages adapter;

    @Before
    public void initialize() {
        ArrayList<ImageContainer> imageList = new ArrayList<>();
        imageList.add(new ImageContainer("somepath",new Date(),1000,"ducky"));
        imageList.add(new ImageContainer("somepath",new Date(),2000,"donald duck"));
        imageList.add(new ImageContainer("somepath",new Date(),1000,"tick"));
        imageList.add(new ImageContainer("/path/trick",new Date(),1500,"trick"));
        imageList.add(new ImageContainer("somepath",new Date(),2300,"track"));
        imageList.add(new ImageContainer("somepath",new Date(),5000,"dagobert duck"));
        imageList.add(new ImageContainer("somepath",new Date(),2400,"daisy duck"));
        imageList.add(new ImageContainer("somepath",new Date(),130,"gustav gans"));
        imageList.add(new ImageContainer("somepath",new Date(),1111,"gundel gauckele"));
        imageList.add(new ImageContainer("somepath",new Date(),1234,"oma duck"));

        adapter=new AdapterImages(imageList);
    }

    @Test
    public void findPicture(){

        String image = adapter.searchPictures("trick");
        assertEquals(image,"/path/trick");
    }





}
