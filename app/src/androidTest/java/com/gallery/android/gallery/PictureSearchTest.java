package com.gallery.android.gallery;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class PictureSearchTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testSearchbarVisible() {
        onView(withId(R.id.search_bar)).check(matches(isDisplayed()));

    }


    @Test
    public void testSearchPictures() {



        activityTestRule.getActivity().editText.setText("ashwarrior.jpg");

        BaseInputConnection inputConnection = new BaseInputConnection(activityTestRule.getActivity().editText, true);
        inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));

        try {
            TimeUnit.SECONDS.sleep(3);
        }
        catch(java.lang.InterruptedException e){
            System.out.println(e.fillInStackTrace());
        }


        String search = activityTestRule.getActivity().editText.getText().toString();

        assertEquals("ashwarrior.jpg", search);



    }


}
