package com.gallery.android.gallery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class ShareButtonTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);
    @Test
    public void testDialogIsShown(){
        try {
            onView(withId(R.id.idImage)).perform(click());
        }
        catch(NoMatchingViewException exception){
            String name = "test.png";
            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() ;
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
            Intent tryAgain=new Intent();
            activityTestRule.launchActivity(tryAgain);
            onView(withId(R.id.idImage)).perform(click());
        }
        onView(withId(R.id.shareButton)).perform(click());
        onView(withText("Share Using")).check(matches(isDisplayed()));

        //delete test.png
        String name = "test.png";
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();
        File file = new File(path, name);
        if(file.exists())
        {
            file.delete();
        }
    }
}
