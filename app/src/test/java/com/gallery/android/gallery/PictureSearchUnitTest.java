package com.gallery.android.gallery;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class PictureSearchUnitTest {

    AdapterImages adapter;

    @Before
    public void initialize() {
        ArrayList<ImageContainer> imageList = new ArrayList<>();
        imageList.add(new ImageContainer("somepath",new SimpleDateFormat(),"ducky"));
        imageList.add(new ImageContainer("somepath",new SimpleDateFormat(),"donald duck"));
        imageList.add(new ImageContainer("somepath",new SimpleDateFormat(),"tick"));
        imageList.add(new ImageContainer("somepath",new SimpleDateFormat(),"trick"));
        imageList.add(new ImageContainer("somepath",new SimpleDateFormat(),"track"));
        imageList.add(new ImageContainer("somepath",new SimpleDateFormat(),"dagobert duck"));
        imageList.add(new ImageContainer("somepath",new SimpleDateFormat(),"daisy duck"));
        imageList.add(new ImageContainer("somepath",new SimpleDateFormat(),"gustav gans"));
        imageList.add(new ImageContainer("somepath",new SimpleDateFormat(),"gundel gauckele"));
        imageList.add(new ImageContainer("somepath",new SimpleDateFormat(),"oma duck"));

        adapter=new AdapterImages(imageList);
    }

    @Test
    public void findPicture(){

        ImageContainer image = adapter.searchPictures("trick");
        assertEquals(image.getFilename(),"trick");
    }





}
