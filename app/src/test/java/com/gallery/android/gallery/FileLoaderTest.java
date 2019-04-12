package com.gallery.android.gallery;

import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;

public class FileLoaderTest {
    FileLoader f=new FileLoader();

    @Test
    public void testFileLoader() {
        assertNotNull(f);
    }
}
