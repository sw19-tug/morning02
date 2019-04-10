package com.gallery.android.gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ShowPictureInFullscreenTest {

    @BeforeClass
    public static void setUpClass() {
        //create test.png
        String name = "test.png";
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();
        File dir = new File(path);
        if(!dir.exists() && !dir.isDirectory()){
            if(!dir.mkdirs())
                System.out.println("ERROR: Not able to create a test image!");
        }
        File dest = new File(path, name);
        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        for(int x = 0; x < 100; x++){
            for(int y = 0; y < 100; y++){
                bitmap.setPixel(x, y, Color.rgb(2, 100, 56));
            }
        }

        try {
            FileOutputStream fos = new FileOutputStream(dest);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void tearDownClass() throws IOException {
        //delete test.png
        String name = "test.png";
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();
        File file = new File(path, name);
        if(file.exists())
        {
            file.delete();
        }
    }

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testRecylerViewVisible() throws Exception {
        // clicks on a picture and checks if it is opened
        onView(withId(R.id.RecyclerId)).check(matches(isDisplayed()));
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.gallery.android.gallery", appContext.getPackageName());
    }

    @Test
    public void testClick() throws Throwable, InterruptedException {
        RecyclerView recycler_view = activityTestRule.getActivity().findViewById(R.id.RecyclerId);
        AdapterImages adapter_images = (AdapterImages) recycler_view.getAdapter();
        for (int i = 0; i < adapter_images.getItemCount(); i++ ) {
            runOnUiThread(new MyRunnable(recycler_view, i) {
                public void run() {
                    this.resycler_view.findViewHolderForAdapterPosition(adapter_position).itemView.performClick();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex1) {
                        return;
                    }
                }
            });
            onView(withId(R.id.fullscreen_image_view)).check(matches(isDisplayed()));
        }
    }
}
