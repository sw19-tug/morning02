package com.gallery.android.gallery;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.support.test.runner.AndroidJUnit4;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.support.test.rule.ActivityTestRule;
import android.view.KeyEvent;
import android.view.inputmethod.BaseInputConnection;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import static junit.framework.TestCase.assertEquals;

@RunWith(AndroidJUnit4.class)
public class PictureSearchTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @BeforeClass
    public static void setUpClass() {
        TestHelper.createFile("test1.png");
        TestHelper.createFile("test2.png");
    }

    @AfterClass
    public static void tearDownClass() throws IOException {
        TestHelper.deleteFile("test1.png");
        TestHelper.deleteFile("test2.png");
    }

    @Test
    public void testSearchbarVisible() {
        onView(withId(R.id.search_bar)).check(matches(isDisplayed()));
    }

    @Test
    public void testSearchPictures() {
        String name = "test1.png";

        final EditText textView = activityTestRule.getActivity().findViewById(R.id.search_bar);
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView.setText("test1.png");
                }
            });
        } catch (java.lang.Throwable e) {
                e.printStackTrace();
        }

        BaseInputConnection inputConnection = new BaseInputConnection(activityTestRule.getActivity().editText, true);
        inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));

        try {
            TimeUnit.SECONDS.sleep(3);
        }
        catch(java.lang.InterruptedException e){
            System.out.println(e.fillInStackTrace());
        }
        String search = textView.getText().toString();
        assertEquals("Found: test1.png", search);

        TestHelper.deleteFile(name);
    }
}
