package com.gallery.android.gallery;

import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;

public class FileDeleterTest {
    FileDeleter fd=new FileDeleter();

    @Test
    public void testFileLoader() {
        assertNotNull(fd);
    }
}
