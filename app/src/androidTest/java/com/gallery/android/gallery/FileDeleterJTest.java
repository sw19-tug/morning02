package com.gallery.android.gallery;

import android.Manifest;
import android.os.Environment;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
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
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(AndroidJUnit4.class)
public class FileDeleterJTest {

    private ActivityTestRule<MainActivity> activityTestRule;

    @Rule
    public final TestRule chain = RuleChain
            .outerRule(GrantPermissionRule.grant(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE))
            .around(activityTestRule =new IntentsTestRule<MainActivity>(MainActivity.class) {
                @Override
                protected void beforeActivityLaunched() {
                    TestHelper.createFile("testClick.png");
                }
            });
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

        onView(withId(R.id.idImage)).perform(click());
        Thread.sleep(100);
        onView(withId(R.id.popupMenu)).perform(click());
        Thread.sleep(100);
        onView(withText("Delete")).check(matches(isDisplayed()));
    }

    @Test
     public void dialog() throws InterruptedException {
        onView(withId(R.id.idImage)).perform(click());
        Thread.sleep(100);
        onView(withId(R.id.popupMenu)).perform(click());
        Thread.sleep(100);
        onView(withText("Delete")).check(matches(isDisplayed()));
        onView(withText("Delete")).perform(click());
        Thread.sleep(100);
        onView(withText("Are you sure?")).check(matches(isDisplayed()));
    }

    @Test
    public void testDialogClick() throws InterruptedException {
        onView(withId(R.id.idImage)).perform(click());
        Thread.sleep(100);
        onView(withId(R.id.popupMenu)).perform(click());
        Thread.sleep(100);
        onView(withText("Delete")).perform(click());
        Thread.sleep(100);
        onView(withText("no")).perform(click());
        Thread.sleep(100);
        onView(withId(R.id.fullscreen_image_view)).check(matches(isDisplayed()));
        onView(withId(R.id.popupMenu)).perform(click());
        Thread.sleep(100);
        onView(withText("Delete")).perform(click());
        Thread.sleep(100);
        onView(withText("yes")).perform(click());
        Thread.sleep(100);
        onView(withId(R.id.activity_main)).check(matches(isDisplayed()));
    }

    @Test
    public void checkList () throws InterruptedException {
        RecyclerView rView=activityTestRule.getActivity().recyclerImages;
        String firstImagePath = ((AdapterImages)(rView.getAdapter())).getListImages().get(0).getPath();
        onView(withId(R.id.idImage)).perform(click());
        Thread.sleep(100);
        onView(withId(R.id.popupMenu)).perform(click());
        Thread.sleep(100);
        onView(withText("Delete")).perform(click());
        Thread.sleep(100);
        onView(withText("yes")).perform(click());
        AdapterImages adapterImages = ((AdapterImages)rView.getAdapter());
        if(!adapterImages.getListImages().isEmpty())
        {
            String afterImagePath = adapterImages.getListImages().get(0).getPath();
            assertNotEquals(firstImagePath,afterImagePath);
        }
    }
}
