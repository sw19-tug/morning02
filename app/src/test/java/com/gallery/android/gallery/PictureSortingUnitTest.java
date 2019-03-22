package com.gallery.android.gallery;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.hasProperty;


import java.util.ArrayList;

public class PictureSortingUnitTest {

    @Test
    public void hasMemberVariables
    {
        //List<dummyimageclass> imageList = new ArrayList<>();
        Dummyimageclass image = new Dummyimageclass();
        assertThat(image, hasProperty("filename"));
        assertThat(image, hasProperty("date"));
        assertThat(image, hasProperty("size"));
    }

}
