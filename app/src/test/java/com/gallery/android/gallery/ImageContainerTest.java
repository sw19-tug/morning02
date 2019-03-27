package com.gallery.android.gallery;

import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;

public class ImageContainerTest {

    ImageContainer imageContainer =new ImageContainer();

    @Test
    public void testImageContainer() {
        assertNotNull(imageContainer);
    }
}
