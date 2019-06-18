package com.gallery.android.gallery;

import org.junit.Test;

import java.util.Date;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class ImageContainerTest {

    ImageContainer imageContainer =new ImageContainer("///",new Date(),1000,"dummyfile");

    @Test
    public void testImageContainer() {

        assertNotNull(imageContainer);
        assertEquals(imageContainer.getPath(),"///");
        assertEquals(imageContainer.getSize(),1000);
        assertEquals(imageContainer.getFilename(),"dummyfile");
    }
}
