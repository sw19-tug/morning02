package com.gallery.android.gallery;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class PopupButtonTest {
    @Rule
    private ActivityTestRule<MainActivity> activityTestRule;

    @Test
    public void buttonExistsTest(){
        onView(withId(R.id.idImage)).perform(click());
        onView(withId(R.id.popupMenu)).check(matches(isDisplayed()));
    }
}
