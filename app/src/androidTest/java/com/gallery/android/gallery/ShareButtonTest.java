package com.gallery.android.gallery;

import android.content.Intent;
import android.os.Handler;
import android.support.test.rule.ActivityTestRule;
import android.widget.Button;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class ShareButtonTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);
    @Test
    public void testDialogIsShown(){
        final Button button = activityTestRule.getActivity().findViewById(R.id.shareButton);

        try {
            activityTestRule.runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    button.performClick();

                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }


        onView(withText("Share Using")).check(matches(isDisplayed()));

    }
}
