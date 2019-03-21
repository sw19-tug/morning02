package com.gallery.android.gallery;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertFalse;

@RunWith(AndroidJUnit4.class)
public class FileLoaderJTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);
    @Test
    public void testPathsAreRetrieved(){
        FileLoader f=new FileLoader();
        assertNotNull(f);
        List<String> paths=f.getImagesPaths();
        assertNotNull(paths);
        assertFalse(paths.isEmpty());

    }
}
