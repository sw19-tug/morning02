package com.gallery.android.gallery;

import android.content.Intent;
import android.os.Handler;
import android.support.test.rule.ActivityTestRule;
import android.widget.Button;

import org.junit.Rule;
import org.junit.Test;

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
        //final ImageView image = activityTestRule.getActivity().findViewById(R.id.idImage);
        onView(withId(R.id.idImage)).perform(click());

        onView(withId(R.id.shareButton)).perform(click());

        onView(withText("Share Using")).check(matches(isDisplayed()));

    }
}
