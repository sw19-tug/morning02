package com.gallery.android.gallery;

import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertFalse;

public class FileLoaderTest {
    FileLoader f=new FileLoader();

    @Test
    public void testFileLoader() {
        assertNotNull(f);
    }
}
