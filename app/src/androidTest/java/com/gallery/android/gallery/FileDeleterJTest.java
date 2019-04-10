package com.gallery.android.gallery;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertFalse;

@RunWith(AndroidJUnit4.class)
public class FileDeleterJTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);
    @Test
    public void testPathsAreRetrieved(){
        FileDeleter fd=new FileDeleter();
        assertNotNull(fd);
        //Create Test File to delete
        String path ="/storage/sdcard/DCIM/test.txt";
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(path);
            byte[] buffer = "test file".getBytes();
            stream.write(buffer, 0, buffer.length);
            stream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //delete the testfile
        assertFalse(fd.delete("/storage/sdcard/DCIM/test.txt") == false);
    }
}
