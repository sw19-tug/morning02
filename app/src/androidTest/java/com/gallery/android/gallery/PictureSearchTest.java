package com.gallery.android.gallery;

import android.Manifest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.KeyEvent;
import android.view.inputmethod.BaseInputConnection;
import android.widget.EditText;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import static junit.framework.TestCase.assertEquals;

@RunWith(AndroidJUnit4.class)
public class PictureSearchTest {
    private ActivityTestRule<MainActivity> activityTestRule;

    @Rule
    public final TestRule chain = RuleChain
            .outerRule(GrantPermissionRule.grant(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE))
            .around(activityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class) {
                @Override
                protected void beforeActivityLaunched() {
                    TestHelper.createFile("test1.jpg");
                    TestHelper.createFile("test2.jpg");
                }
            });

    @AfterClass
    public static void tearDownClass() {
        TestHelper.deleteFile("test1.jpg");
        TestHelper.deleteFile("test2.jpg");
    }

    @Test
    public void testSearchbarVisible() {
        onView(withId(R.id.search_bar)).check(matches(isDisplayed()));
    }

    //test unused until we find out how to create pictures in tests
   /* @Test
    public void testSearchPictures() {
        String name = "test1.jpg";

        final EditText textView = activityTestRule.getActivity().findViewById(R.id.search_bar);
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView.setText("test1.jpg");
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
            e.printStackTrace();
        }
        onView(withId(R.id.fullscreen_image_view)).check(matches(isDisplayed()));

        TestHelper.deleteFile(name);
    }*/
}
