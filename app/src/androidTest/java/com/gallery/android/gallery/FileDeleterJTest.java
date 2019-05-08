package com.gallery.android.gallery;

import android.Manifest;
import android.os.Environment;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertNotEquals;

@RunWith(AndroidJUnit4.class)
public class FileDeleterJTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE);

    @Test
    public void testPathsAreRetrieved(){
        FileDeleter fd=new FileDeleter();
        assertNotNull(fd);
        //Create Test File to delete
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();
        File dir = new File(path);
        if(!dir.exists() && !dir.isDirectory()){
            if(!dir.mkdirs())
                System.out.println("ERROR: Not able to create a test image!");
        }
        path = path + "/test.txt";
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

        //delete the test file
        assertNotEquals(false, fd.delete(path));
    }

    @Test
    public void testButtonVisible() throws InterruptedException {

        onView(withId(R.id.activity_main)).perform(click());

        Thread.sleep(100);

        onView(withId(R.id.delete_button)).check(matches(isDisplayed()));
    }
}
